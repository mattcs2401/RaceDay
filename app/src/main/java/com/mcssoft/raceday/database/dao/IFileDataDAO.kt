package com.mcssoft.raceday.database.dao

import androidx.room.*
import com.mcssoft.raceday.database.entity.FileMetaData

@Dao
interface IFileDataDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFile(file: FileMetaData): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateFile(file: FileMetaData): Int

    @Query("select * from file_meta_data where _id = :id")
    fun getFileData(id: Long): FileMetaData

    @Query("select * from file_meta_data")
    fun getFileData(): List<FileMetaData>

    @Delete()
    fun deleteFile(file: FileMetaData): Int

    @Query("select count(*) from file_meta_data")
    fun getCount(): Int
}
