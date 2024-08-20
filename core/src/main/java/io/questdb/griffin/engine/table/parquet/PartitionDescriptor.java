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

package io.questdb.griffin.engine.table.parquet;

import io.questdb.cairo.SymbolMapWriter;
import io.questdb.std.*;
import io.questdb.std.str.DirectUtf8Sink;
import io.questdb.std.str.Utf8Sequence;

public abstract class PartitionDescriptor implements QuietCloseable {
    protected DirectLongList columnAddrs = new DirectLongList(16, MemoryTag.NATIVE_DEFAULT);
    protected DirectIntList columnIds = new DirectIntList(16, MemoryTag.NATIVE_DEFAULT);
    protected DirectIntList columnNameLengths = new DirectIntList(16, MemoryTag.NATIVE_DEFAULT);
    protected DirectUtf8Sink columnNames = new DirectUtf8Sink(32);
    protected DirectLongList columnSecondaryAddrs = new DirectLongList(16, MemoryTag.NATIVE_DEFAULT);
    protected DirectLongList columnSecondarySizes = new DirectLongList(16, MemoryTag.NATIVE_DEFAULT);
    protected DirectLongList columnSizes = new DirectLongList(16, MemoryTag.NATIVE_DEFAULT);
    protected DirectLongList columnTops = new DirectLongList(16, MemoryTag.NATIVE_DEFAULT);
    protected DirectIntList columnTypes = new DirectIntList(16, MemoryTag.NATIVE_DEFAULT);
    protected DirectLongList symbolOffsetsAddrs = new DirectLongList(16, MemoryTag.NATIVE_DEFAULT);
    protected DirectLongList symbolOffsetsSizes = new DirectLongList(16, MemoryTag.NATIVE_DEFAULT);
    protected DirectUtf8Sink tableName = new DirectUtf8Sink(16);
    protected long partitionRowCount;
    protected int timestampIndex = -1;

    public abstract void clear();

    @Override
    public abstract void close();

    public void addColumn(
            final CharSequence columnName,
            int columnType,
            int columnId,
            long columnTop,
            long columnAddr,
            long columnSize,
            long columnSecondaryAddr,
            long columnSecondarySize,
            long symbolOffsetsAddr,
            long symbolOffsetsSize
    ) {
        final int startSize = columnNames.size();
        columnNames.put(columnName);
        columnNameLengths.add(columnNames.size() - startSize);
        columnIds.add(columnId);
        columnTops.add(columnTop);
        columnTypes.add(columnType);
        columnAddrs.add(columnAddr);
        columnSizes.add(columnSize);
        columnSecondaryAddrs.add(columnSecondaryAddr);
        columnSecondarySizes.add(columnSecondarySize);
        symbolOffsetsAddrs.add(symbolOffsetsAddr);
        symbolOffsetsSizes.add(symbolOffsetsSize);
    }

    public long getColumnAddressesPtr() {
        return columnAddrs.getAddress();
    }

    public int getColumnCount() {
        return (int) columnAddrs.size();
    }

    public long getColumnIdsPtr() {
        return columnIds.getAddress();
    }

    public long getColumnNameLengthsPtr() {
        return columnNameLengths.getAddress();
    }

    public long getColumnNamesPtr() {
        return columnNames.ptr();
    }

    public int getColumnNamesSize() {
        return columnNames.size();
    }

    public long getColumnSecondaryAddressesPtr() {
        return columnSecondaryAddrs.getAddress();
    }

    public long getColumnSecondarySizesPtr() {
        return columnSecondarySizes.getAddress();
    }

    public long getColumnSizesPtr() {
        return columnSizes.getAddress();
    }

    public long getColumnTopsPtr() {
        return columnTops.getAddress();
    }

    public long getColumnTypesPtr() {
        return columnTypes.getAddress();
    }

    public long getPartitionRowCount() {
        return partitionRowCount;
    }

    public long getSymbolOffsetsAddressesPtr() {
        return symbolOffsetsAddrs.getAddress();
    }

    public long getSymbolOffsetsSizesPtr() {
        return symbolOffsetsSizes.getAddress();
    }

    public Utf8Sequence getTableName() {
        return tableName;
    }

    public int getTimestampIndex() {
        return timestampIndex;
    }

    public PartitionDescriptor of(final CharSequence tableName, long partitionRowCount, int timestampIndex) {
        this.clear();
        this.tableName.put(tableName);
        this.partitionRowCount = partitionRowCount;
        this.timestampIndex = timestampIndex;
        return this;
    }
}