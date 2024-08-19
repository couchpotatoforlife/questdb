use std::fs::File;
use std::path::Path;
use std::{fs, slice};

use crate::parquet_write::file::{create_row_group, ParquetWriter, WriteOptions};
use crate::parquet_write::schema::{to_encodings, Column, Partition};
use crate::parquet_write::ParquetError;
use anyhow::Context;
use jni::objects::JClass;
use jni::sys::{jboolean, jint, jlong, jshort};
use jni::JNIEnv;
use parquet2::compression::{BrotliLevel, CompressionOptions, GzipLevel, ZstdLevel};
use parquet2::metadata::SortingColumn;
use parquet2::read::read_metadata;
use parquet2::write;
use parquet2::write::{ParquetFile, Version};

fn read_utf8_encoded_string_list(
    count: usize,
    strings_sink: *const u8,
    strings_len: usize,
    sizes: *const i32,
) -> Vec<&'static str> {
    let mut strings = Vec::new();
    let mut utf8_sink =
        unsafe { std::str::from_utf8_unchecked(slice::from_raw_parts(strings_sink, strings_len)) };

    let sizes = unsafe { slice::from_raw_parts(sizes, count) };
    for size in sizes {
        let (s, tail) = utf8_sink.split_at(*size as usize);
        strings.push(s);
        utf8_sink = tail;
    }

    strings
}

#[no_mangle]
pub extern "system" fn Java_io_questdb_griffin_engine_table_parquet_PartitionEncoder_encodePartition(
    mut env: JNIEnv,
    _class: JClass,
    table_name_ptr: *const u8,
    table_name_size: jint,
    col_count: jint,
    col_names: *const u8,
    col_names_len: i32,
    col_name_sizes_ptr: *const i32,
    col_types_ptr: *const i32,
    col_ids_ptr: *const i32,
    timestamp_index: jint,
    col_tops_ptr: *const i64,
    primary_col_addrs_ptr: *const *const u8,
    primary_col_sizes_ptr: *const i64,
    secondary_col_addrs_ptr: *const *const u8,
    secondary_col_sizes_ptr: *const i64,
    symbol_offsets_addrs_ptr: *const *const u64,
    symbol_offsets_sizes_ptr: *const i64,
    row_count: jlong,
    dest_path: *const u8,
    dest_path_len: i32,
    compression_codec: jlong,
    statistics_enabled: jboolean,
    row_group_size: jlong,
    data_page_size: jlong,
    version: jint,
) {
    let encode = || -> anyhow::Result<()> {
        let col_count = col_count as usize;
        let col_names = read_utf8_encoded_string_list(
            col_count,
            col_names,
            col_names_len as usize,
            col_name_sizes_ptr,
        );
        let col_types = unsafe { slice::from_raw_parts(col_types_ptr, col_count) };
        let col_tops = unsafe { slice::from_raw_parts(col_tops_ptr, col_count) };
        let col_ids = unsafe { slice::from_raw_parts(col_ids_ptr, col_count) };

        let primary_col_addrs_slice =
            unsafe { slice::from_raw_parts(primary_col_addrs_ptr, col_count) };
        let primary_col_sizes_slice =
            unsafe { slice::from_raw_parts(primary_col_sizes_ptr, col_count) };

        let secondary_col_addrs_slice =
            unsafe { slice::from_raw_parts(secondary_col_addrs_ptr, col_count) };
        let secondary_col_sizes_slice =
            unsafe { slice::from_raw_parts(secondary_col_sizes_ptr, col_count) };

        let symbol_offsets_addrs_slice =
            unsafe { slice::from_raw_parts(symbol_offsets_addrs_ptr, col_count) };
        let symbol_offsets_sizes_slice =
            unsafe { slice::from_raw_parts(symbol_offsets_sizes_ptr, col_count) };

        let dest_path = unsafe {
            std::str::from_utf8_unchecked(slice::from_raw_parts(dest_path, dest_path_len as usize))
        };

        let dest_path = Path::new(&dest_path);

        let row_count = row_count as usize;

        let mut columns = vec![];
        for i in 0..col_count {
            let col_id = col_ids[i];
            let col_name = col_names[i];
            let col_type = col_types[i];
            let col_top = col_tops[i];

            let primary_col_addr = primary_col_addrs_slice[i];
            let primary_col_size = primary_col_sizes_slice[i];

            let secondary_col_addr = secondary_col_addrs_slice[i];
            let secondary_col_size = secondary_col_sizes_slice[i];

            let symbol_offsets_addr = symbol_offsets_addrs_slice[i];
            let symbol_offsets_size = symbol_offsets_sizes_slice[i];

            let column = Column::from_raw_data(
                col_id,
                col_name,
                col_type,
                col_top,
                row_count,
                primary_col_addr,
                primary_col_size as usize,
                secondary_col_addr,
                secondary_col_size as usize,
                symbol_offsets_addr,
                symbol_offsets_size as usize,
            )?;

            columns.push(column);
        }

        let table = unsafe {
            std::str::from_utf8_unchecked(slice::from_raw_parts(
                table_name_ptr,
                table_name_size as usize,
            ))
        }
        .to_string();
        let partition = Partition { table, columns };

        let compression_options =
            compression_from_i64(compression_codec).context("CompressionCodec")?;
        let statistics_enabled = statistics_enabled != 0;
        let row_group_size = if row_group_size > 0 {
            Some(row_group_size as usize)
        } else {
            None
        };
        let data_page_size = if data_page_size > 0 {
            Some(data_page_size as usize)
        } else {
            None
        };

        let version = version_from_i32(version).context("Version")?;

        let mut file = File::create(dest_path).with_context(|| {
            format!(
                "Could not send create parquet file for {}",
                dest_path.display()
            )
        })?;

        let sorting_columns = if timestamp_index != -1 {
            Some(vec![SortingColumn::new(timestamp_index, false, false)])
        } else {
            None
        };

        ParquetWriter::new(&mut file)
            .with_version(version)
            .with_compression(compression_options)
            .with_statistics(statistics_enabled)
            .with_row_group_size(row_group_size)
            .with_data_page_size(data_page_size)
            .with_sorting_columns(sorting_columns)
            .finish(partition)
            .map(|_| ())
            .context("ParquetWriter::finish failed")
    };

    match encode() {
        Ok(_) => (),
        Err(err) => {
            if let Some(jni_err) = err.downcast_ref::<jni::errors::Error>() {
                match jni_err {
                    jni::errors::Error::JavaException => {
                        // Already thrown.
                    }
                    _ => {
                        let msg = format!("Failed to encode partition: {:?}", jni_err);
                        env.throw_new("java/lang/RuntimeException", msg)
                            .expect("failed to throw exception");
                    }
                }
            } else {
                let msg = format!("Failed to encode partition: {:?}", err);
                env.throw_new("java/lang/RuntimeException", msg)
                    .expect("failed to throw exception");
            }
        }
    }
}

fn version_from_i32(value: i32) -> Result<Version, ParquetError> {
    Ok(match value {
        1 => Version::V1,
        2 => Version::V2,
        _ => {
            return Err(ParquetError::OutOfSpec(
                "Invalid value for Version".to_string(),
            ))
        }
    })
}

/// The `i64` value is expected to encode two `i32` values:
/// - The higher 32 bits represent the `level_id`.
/// - The lower 32 bits represent the optional `codec_id`.
///   `let value: i64 = (3 << 32) | 2;` Gzip with level 3.
fn compression_from_i64(value: i64) -> Result<CompressionOptions, ParquetError> {
    let codec_id = value as i32;
    let level_id = (value >> 32) as i32;
    Ok(match codec_id {
        0 => CompressionOptions::Uncompressed,
        1 => CompressionOptions::Snappy,
        2 => CompressionOptions::Gzip(Some(GzipLevel::try_new(level_id as u8)?)),
        3 => CompressionOptions::Lzo,
        4 => CompressionOptions::Brotli(Some(BrotliLevel::try_new(level_id as u32)?)),
        5 => CompressionOptions::Lz4,
        6 => CompressionOptions::Zstd(Some(ZstdLevel::try_new(level_id)?)),
        7 => CompressionOptions::Lz4Raw,
        _ => {
            return Err(ParquetError::OutOfSpec(
                "Invalid value for CompressionCodec".to_string(),
            ))
        }
    })
}

#[no_mangle]
pub extern "system" fn Java_io_questdb_griffin_engine_table_parquet_PartitionEncoder_updatePartition(
    mut env: JNIEnv,
    _class: JClass,
    row_group_id: jshort,
    col_count: jint,
    col_names: *const u8,
    col_names_len: i32,
    col_name_sizes_ptr: *const i32,
    col_types_ptr: *const i32,
    col_ids_ptr: *const i32,
    _timestamp_index: jint,
    col_tops_ptr: *const i64,
    primary_col_addrs_ptr: *const *const u8,
    primary_col_sizes_ptr: *const i64,
    secondary_col_addrs_ptr: *const *const u8,
    secondary_col_sizes_ptr: *const i64,
    symbol_offsets_addrs_ptr: *const *const u64,
    symbol_offsets_sizes_ptr: *const i64,
    row_count: jlong,
    dest_path: *const u8,
    dest_path_len: i32,
) {
    let update = || -> anyhow::Result<()> {
        let row_count = row_count as usize;
        let dest_path = unsafe {
            std::str::from_utf8_unchecked(slice::from_raw_parts(dest_path, dest_path_len as usize))
        };

        let col_count = col_count as usize;
        let col_names = read_utf8_encoded_string_list(
            col_count,
            col_names,
            col_names_len as usize,
            col_name_sizes_ptr,
        );
        let col_types = unsafe { slice::from_raw_parts(col_types_ptr, col_count) };
        let col_tops = unsafe { slice::from_raw_parts(col_tops_ptr, col_count) };
        let col_ids = unsafe { slice::from_raw_parts(col_ids_ptr, col_count) };

        let primary_col_addrs_slice =
            unsafe { slice::from_raw_parts(primary_col_addrs_ptr, col_count) };
        let primary_col_sizes_slice =
            unsafe { slice::from_raw_parts(primary_col_sizes_ptr, col_count) };

        let secondary_col_addrs_slice =
            unsafe { slice::from_raw_parts(secondary_col_addrs_ptr, col_count) };
        let secondary_col_sizes_slice =
            unsafe { slice::from_raw_parts(secondary_col_sizes_ptr, col_count) };

        let symbol_offsets_addrs_slice =
            unsafe { slice::from_raw_parts(symbol_offsets_addrs_ptr, col_count) };
        let symbol_offsets_sizes_slice =
            unsafe { slice::from_raw_parts(symbol_offsets_sizes_ptr, col_count) };

        let mut columns = vec![];
        for i in 0..col_count {
            let col_id = col_ids[i];
            let col_name = col_names[i];
            let col_type = col_types[i];
            let col_top = col_tops[i];

            let primary_col_addr = primary_col_addrs_slice[i];
            let primary_col_size = primary_col_sizes_slice[i];

            let secondary_col_addr = secondary_col_addrs_slice[i];
            let secondary_col_size = secondary_col_sizes_slice[i];

            let symbol_offsets_addr = symbol_offsets_addrs_slice[i];
            let symbol_offsets_size = symbol_offsets_sizes_slice[i];

            let column = Column::from_raw_data(
                col_id,
                col_name,
                col_type,
                col_top,
                row_count,
                primary_col_addr,
                primary_col_size as usize,
                secondary_col_addr,
                secondary_col_size as usize,
                symbol_offsets_addr,
                symbol_offsets_size as usize,
            )?;
            columns.push(column);
        }

        let table = "update".to_string();
        let partition = Partition { table, columns };
        //TODO: validate schema

        let dest_path = Path::new(&dest_path);
        let file_size = fs::metadata(dest_path)?.len();
        let mut file = File::options()
            .read(true)
            .write(true)
            .open(dest_path)
            .with_context(|| {
                format!(
                    "Could not open for update parquet file [path={}]",
                    dest_path.display()
                )
            })?;

        let metadata = read_metadata(&mut file)?;

        let version = Version::V1;
        let created_by = metadata.created_by.clone();
        let schema = metadata.schema_descr.clone();

        let row_group_options = WriteOptions {
            write_statistics: true,
            compression: CompressionOptions::Uncompressed,
            version,
            row_group_size: None,
            data_page_size: None,
        };

        let new_row_group = create_row_group(
            &partition,
            0,
            row_count,
            metadata.schema_descr.fields(),
            &to_encodings(&partition),
            row_group_options,
            false,
        )?;

        let options = write::WriteOptions { write_statistics: true, version: Version::V1 };
        let mut parquet_file = ParquetFile::new_updater(
            &mut file,
            file_size,
            schema,
            options,
            created_by,
            None,
            metadata.into_thrift(),
        );
        parquet_file.replace(new_row_group, Some(row_group_id))?;
        parquet_file.end(None)?;

        Ok(())
    };

    match update() {
        Ok(_) => (),
        Err(err) => {
            if let Some(jni_err) = err.downcast_ref::<jni::errors::Error>() {
                match jni_err {
                    jni::errors::Error::JavaException => {
                        // Already thrown.
                    }
                    _ => {
                        let msg = format!("Failed to update partition: {:?}", jni_err);
                        env.throw_new("java/lang/RuntimeException", msg)
                            .expect("failed to throw exception");
                    }
                }
            } else {
                let msg = format!("Failed to update partition: {:?}", err);
                env.throw_new("java/lang/RuntimeException", msg)
                    .expect("failed to throw exception");
            }
        }
    }
}
