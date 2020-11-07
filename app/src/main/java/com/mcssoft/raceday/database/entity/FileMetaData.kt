package com.mcssoft.raceday.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "file_meta_data")
data class FileMetaData(@ColumnInfo(name = "FileDate") var fileDate: String,
                        @ColumnInfo(name = "FileUrl") var fileUrl: String,
                        @ColumnInfo(name = "FileName")  var fileName: String,
                        @ColumnInfo(name = "FileId")  var fileId: Long) : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long? = null    // value inserted by Room.

    @ColumnInfo(name = "FileType")
    var fileType: String = ""

    @ColumnInfo(name = "FileLocation")
    var fileLocation: String = ""

    override fun toString(): String {
        return super.toString()
        // TBA
    }
}