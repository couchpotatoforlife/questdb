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

package io.questdb.cairo.wal;

import io.questdb.cairo.*;
import io.questdb.cairo.sql.TableRecordMetadata;
import io.questdb.std.LongList;
import org.jetbrains.annotations.NotNull;

public interface MetadataService {
    /**
     * Adds new column to table, which can be either empty or can have data already. When existing columns
     * already have data this function will create ".top" file in addition to column files. ".top" file contains
     * size of partition at the moment of column creation. It must be used to accurately position inside new
     * column when either appending or reading.
     *
     * <b>Failures</b>
     * Adding new column can fail in many situations. None of the failures affect integrity of data that is already in
     * the table but can leave instance of TableWriter in inconsistent state. When this happens function will throw CairoError.
     * Calling code must close TableWriter instance and open another when problems are rectified. Those problems would be
     * either with disk or memory or both.
     * <p>
     * Whenever function throws CairoException application code can continue using TableWriter instance and may attempt to
     * add columns again.
     *
     * <b>Transactions</b>
     * <p>
     * Pending transaction will be committed before function attempts to add column. Even when function is unsuccessful it may
     * still have committed transaction.
     *
     * @param name                    of column either ASCII or UTF8 encoded.
     * @param symbolCapacity          when column type is SYMBOL this parameter specifies approximate capacity for symbol map.
     *                                It should be equal to number of unique symbol values stored in the table and getting this
     *                                value badly wrong will cause performance degradation. Must be power of 2
     * @param symbolCacheFlag         when set to true, symbol values will be cached on Java heap.
     * @param type                    {@link ColumnType}
     * @param isIndexed               configures column to be indexed or not
     * @param indexValueBlockCapacity approximation of number of rows for single index key, must be power of 2
     * @param isSequential            for columns that contain sequential values query optimiser can make assumptions on range searches (future feature)
     */
    void addColumn(
            CharSequence name,
            int type,
            int symbolCapacity,
            boolean symbolCacheFlag,
            boolean isIndexed,
            int indexValueBlockCapacity,
            boolean isSequential,
            SecurityContext securityContext
    );

    default void addColumn(
            CharSequence name,
            int type,
            int symbolCapacity,
            boolean symbolCacheFlag,
            boolean isIndexed,
            int indexValueBlockCapacity,
            boolean isSequential
    ) {
        addColumn(name, type, symbolCapacity, symbolCacheFlag, isIndexed, indexValueBlockCapacity, isSequential, null);
    }

    void addIndex(@NotNull CharSequence columnName, int indexValueBlockSize);

    AttachDetachStatus attachPartition(long partitionTimestamp);

    void changeCacheFlag(int columnIndex, boolean isCacheOn);

    void changeColumnType(
            CharSequence columnName,
            int newType,
            int symbolCapacity,
            boolean symbolCacheFlag,
            boolean isIndexed,
            int indexValueBlockCapacity,
            boolean isSequential,
            SecurityContext securityContext
    );

    boolean convertPartitionToParquet(long partitionTimestamp);

    AttachDetachStatus detachPartition(long partitionTimestamp);

    void disableDeduplication();

    void dropIndex(@NotNull CharSequence columnName);

    void enableDeduplicationWithUpsertKeys(LongList columnsIndexes);

    int getMetaMaxUncommittedRows();

    long getMetaO3MaxLag();

    TableRecordMetadata getMetadata();

    int getPartitionBy();

    TableToken getTableToken();

    UpdateOperator getUpdateOperator();

    void removeColumn(@NotNull CharSequence columnName);

    boolean removePartition(long partitionTimestamp);

    default void renameColumn(@NotNull CharSequence columnName, @NotNull CharSequence newName) {
        renameColumn(columnName, newName, null);
    }

    void renameColumn(@NotNull CharSequence columnName, @NotNull CharSequence newName, SecurityContext securityContext);

    void renameTable(@NotNull CharSequence fromNameTable, @NotNull CharSequence toTableName);

    void setMetaMaxUncommittedRows(int maxUncommittedRows);

    void setMetaO3MaxLag(long o3MaxLagUs);

    void squashPartitions();

    void tick();

}
