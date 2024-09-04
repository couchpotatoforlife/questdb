/*******************************************************************************
 *     ___                  _   ____  ____
 *    / _ \ _   _  ___  ___| |_|  _ \| __ )
 *   | | | | | | |/ _ \/ __| __| | | |  _ \
 *   | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *    \__\_\\__,_|\___||___/\__|____/|____/
 *
 *  Copyright (c) 2014-2019 Appsicle
 *  Copyright (c) 2019-2024 QuestDB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package io.questdb.cairo.sql;

import io.questdb.cairo.CairoException;
import io.questdb.cairo.Reopenable;
import io.questdb.griffin.engine.table.parquet.PartitionDecoder;
import io.questdb.griffin.engine.table.parquet.RowGroupBuffers;
import io.questdb.std.*;
import org.jetbrains.annotations.NotNull;

/**
 * Provides addresses for page frames in both native and Parquet formats.
 * Memory in native page frames is mmapped, so no additional actions are
 * necessary. Parquet frames must be explicitly deserialized into
 * the in-memory native format before being accessed directly or via a Record.
 * Thus, a {@link #navigateTo(int)} call is required before accessing memory
 * that belongs to a page frame.
 * <p>
 * This pool is thread-unsafe as it may hold navigated Parquet partition data,
 * so it shouldn't be shared between multiple threads.
 */
public class PageFrameMemoryPool implements QuietCloseable {
    private static final byte FRAME_MEMORY_MASK = 1 << 2;
    private static final byte RECORD_A_MASK = 1;
    private static final byte RECORD_B_MASK = 1 << 1;
    // LRU cache (most recently used buffers are to the right)
    private final ObjList<ParquetBuffers> cachedParquetBuffers;
    private final PageFrameMemoryImpl frameMemory;
    private final ObjList<ParquetBuffers> freeParquetBuffers;
    private final int parquetCacheSize;
    private final DirectIntList parquetColumnTypes;
    private final PartitionDecoder parquetDecoder;
    private PageFrameAddressCache addressCache;

    public PageFrameMemoryPool() {
        // TODO(puzpuzpuz): move to config
        final int cacheSize = 3;
        try {
            parquetCacheSize = cacheSize;
            cachedParquetBuffers = new ObjList<>(parquetCacheSize);
            freeParquetBuffers = new ObjList<>(parquetCacheSize);
            for (int i = 0; i < parquetCacheSize; i++) {
                freeParquetBuffers.add(new ParquetBuffers());
            }
            frameMemory = new PageFrameMemoryImpl();
            parquetDecoder = new PartitionDecoder();
            parquetColumnTypes = new DirectIntList(16, MemoryTag.NATIVE_DEFAULT);
        } catch (Throwable th) {
            close();
            throw th;
        }
    }

    @Override
    public void close() {
        Misc.free(parquetDecoder);
        Misc.free(parquetColumnTypes);
        freeParquetBuffers.addAll(cachedParquetBuffers);
        cachedParquetBuffers.clear();
        Misc.freeObjListAndKeepObjects(freeParquetBuffers);
        frameMemory.clear();
        addressCache = null;
    }

    /**
     * Navigates to the given frame, potentially deserializing it to in-memory format
     * (for Parquet partitions). After this call, the input record can be used to access
     * any row within the frame.
     */
    public void navigateTo(int frameIndex, PageFrameMemoryRecord record) {
        if (record.getFrameIndex() == frameIndex) {
            return;
        }

        final byte format = addressCache.getFrameFormat(frameIndex);
        if (format == PartitionFormat.NATIVE) {
            record.init(
                    frameIndex,
                    format,
                    addressCache.getRowIdOffset(frameIndex),
                    addressCache.getPageAddresses(frameIndex),
                    addressCache.getAuxPageAddresses(frameIndex),
                    addressCache.getPageSizes(frameIndex),
                    addressCache.getAuxPageSizes(frameIndex)
            );
        } else if (format == PartitionFormat.PARQUET) {
            parquetDecoder.of(addressCache.getParquetFd(frameIndex));
            final byte usageBit = record.getLetter() == PageFrameMemoryRecord.RECORD_A_LETTER ? RECORD_A_MASK : RECORD_B_MASK;
            final ParquetBuffers parquetBuffers = findBuffers(frameIndex, usageBit);
            parquetBuffers.decode(parquetDecoder, parquetColumnTypes, addressCache.getParquetRowGroup(frameIndex));

            record.init(
                    frameIndex,
                    format,
                    addressCache.getRowIdOffset(frameIndex),
                    parquetBuffers.pageAddresses,
                    parquetBuffers.auxPageAddresses,
                    parquetBuffers.pageSizes,
                    parquetBuffers.auxPageSizes
            );
        }
    }

    /**
     * Navigates to the given frame, potentially deserializing it to in-memory format
     * (for Parquet partitions). The returned PageFrameMemory object is a flyweight,
     * so it should be used immediately once returned. This method is useful for later
     * calls to native code.
     * <p>
     * If you need data access via {@link Record} API, use the
     * {@link #navigateTo(int, PageFrameMemoryRecord)} method.
     */
    public PageFrameMemory navigateTo(int frameIndex) {
        if (frameMemory.frameIndex == frameIndex) {
            return frameMemory;
        }

        final byte format = addressCache.getFrameFormat(frameIndex);
        if (format == PartitionFormat.NATIVE) {
            frameMemory.pageAddresses = addressCache.getPageAddresses(frameIndex);
            frameMemory.auxPageAddresses = addressCache.getAuxPageAddresses(frameIndex);
            frameMemory.pageSizes = addressCache.getPageSizes(frameIndex);
            frameMemory.auxPageSizes = addressCache.getAuxPageSizes(frameIndex);
        } else if (format == PartitionFormat.PARQUET) {
            parquetDecoder.of(addressCache.getParquetFd(frameIndex));
            final ParquetBuffers parquetBuffers = findBuffers(frameIndex, FRAME_MEMORY_MASK);
            parquetBuffers.decode(parquetDecoder, parquetColumnTypes, addressCache.getParquetRowGroup(frameIndex));

            frameMemory.pageAddresses = parquetBuffers.pageAddresses;
            frameMemory.auxPageAddresses = parquetBuffers.auxPageAddresses;
            frameMemory.pageSizes = parquetBuffers.pageSizes;
            frameMemory.auxPageSizes = parquetBuffers.auxPageSizes;
        }

        frameMemory.frameIndex = frameIndex;
        frameMemory.frameFormat = format;
        return frameMemory;
    }

    public void of(PageFrameAddressCache addressCache) {
        this.addressCache = addressCache;
        frameMemory.clear();
        parquetColumnTypes.reopen();
        addressCache.copyColumnTypes(parquetColumnTypes);
        for (int i = 0, n = freeParquetBuffers.size(); i < n; i++) {
            freeParquetBuffers.getQuick(i).reopen();
        }
    }

    // We don't use additional data structures to speed up the lookups
    // such as <frame_index, buffers> hash table. That's because we don't
    // expect the cache size to be large.
    @NotNull
    private ParquetBuffers findBuffers(int frameIndex, byte usageBit) {
        // First, clear the usage bit.
        for (int i = 0, n = cachedParquetBuffers.size(); i < n; i++) {
            ParquetBuffers buffers = cachedParquetBuffers.getQuick(i);
            buffers.usageFlags &= (byte) ~usageBit;
        }
        // Next, check if the frame is already in the cache.
        final int cached = cachedParquetBuffers.size();
        for (int i = 0; i < cached; i++) {
            ParquetBuffers buffers = cachedParquetBuffers.getQuick(i);
            if (buffers.frameIndex == frameIndex) {
                buffers.usageFlags |= usageBit;
                // Preserve LRU order.
                cachedParquetBuffers.setQuick(i, cachedParquetBuffers.getQuick(cached - 1));
                cachedParquetBuffers.setQuick(cached - 1, buffers);
                return buffers;
            }
        }
        // Check free buffers.
        final int free = freeParquetBuffers.size();
        if (free > 0) {
            ParquetBuffers buffers = freeParquetBuffers.getQuick(free - 1);
            freeParquetBuffers.remove(free - 1);
            buffers.frameIndex = frameIndex;
            buffers.usageFlags = usageBit;
            cachedParquetBuffers.add(buffers);
            return buffers;
        }
        // Finally, try to find an unused buffer in the cache.
        for (int i = 0; i < cached; i++) {
            ParquetBuffers buffers = cachedParquetBuffers.getQuick(i);
            if (buffers.usageFlags == 0) {
                buffers.frameIndex = frameIndex;
                buffers.usageFlags = usageBit;
                // Preserve LRU order.
                cachedParquetBuffers.setQuick(i, cachedParquetBuffers.getQuick(cached - 1));
                cachedParquetBuffers.setQuick(cached - 1, buffers);
                return buffers;
            }
        }
        // Give up.
        throw CairoException.critical(0)
                .put("insufficient memory pool size [size=").put(parquetCacheSize)
                .put(", usageBit=").put(usageBit)
                .put(']');
    }

    private static class ParquetBuffers implements QuietCloseable, Reopenable {
        private final LongList auxPageAddresses = new LongList();
        private final LongList auxPageSizes = new LongList();
        private final LongList pageAddresses = new LongList();
        private final LongList pageSizes = new LongList();
        private final RowGroupBuffers rowGroupBuffers = new RowGroupBuffers();
        private int frameIndex = -1;
        // Contains bits FRAME_MEMORY_MASK, RECORD_A_MASK and RECORD_B_MASK.
        private byte usageFlags;

        public void clearAddresses() {
            pageAddresses.clear();
            pageSizes.clear();
            auxPageAddresses.clear();
            auxPageSizes.clear();
        }

        @Override
        public void close() {
            Misc.free(rowGroupBuffers);
            clearAddresses();
            usageFlags = 0;
            frameIndex = -1;
        }

        public void decode(PartitionDecoder parquetDecoder, DirectIntList parquetColumnTypes, int rowGroup) {
            parquetDecoder.decodeRowGroup(rowGroupBuffers, parquetColumnTypes, rowGroup);
            // TODO(puzpuzpuz): we need to map Parquet columns to what's required by the query
            clearAddresses();
            for (int i = 0, n = parquetDecoder.getMetadata().columnCount(); i < n; i++) {
                pageAddresses.add(rowGroupBuffers.getChunkDataPtr(i));
                pageSizes.add(rowGroupBuffers.getChunkDataSize(i));
                auxPageAddresses.add(rowGroupBuffers.getChunkAuxPtr(i));
                auxPageSizes.add(rowGroupBuffers.getChunkAuxSize(i));
            }
        }

        @Override
        public void reopen() {
            rowGroupBuffers.reopen();
        }
    }

    private class PageFrameMemoryImpl implements PageFrameMemory, Mutable {
        private LongList auxPageAddresses;
        private LongList auxPageSizes;
        private byte frameFormat = -1;
        private int frameIndex = -1;
        private LongList pageAddresses;
        private LongList pageSizes;

        @Override
        public void clear() {
            frameIndex = -1;
            frameFormat = -1;
            pageAddresses = null;
            auxPageAddresses = null;
            pageSizes = null;
            auxPageSizes = null;
        }

        @Override
        public long getAuxPageAddress(int columnIndex) {
            return auxPageAddresses.getQuick(columnIndex);
        }

        @Override
        public LongList getAuxPageAddresses() {
            return auxPageAddresses;
        }

        @Override
        public LongList getAuxPageSizes() {
            return auxPageSizes;
        }

        @Override
        public int getColumnCount() {
            return addressCache.getColumnCount();
        }

        @Override
        public byte getFrameFormat() {
            return frameFormat;
        }

        @Override
        public int getFrameIndex() {
            return frameIndex;
        }

        @Override
        public long getPageAddress(int columnIndex) {
            return pageAddresses.getQuick(columnIndex);
        }

        @Override
        public LongList getPageAddresses() {
            return pageAddresses;
        }

        @Override
        public long getPageSize(int columnIndex) {
            return pageSizes.getQuick(columnIndex);
        }

        @Override
        public LongList getPageSizes() {
            return pageSizes;
        }

        @Override
        public long getRowIdOffset() {
            return addressCache.getRowIdOffset(frameIndex);
        }
    }
}
