package com.mcssoft.racedaybasic.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Scratching",
    indices = [Index(value = ["rId"])],
    foreignKeys = [
        ForeignKey(
            entity = Race::class,
            parentColumns = ["_id"],
            childColumns = ["rId"],
            onDelete = ForeignKey.CASCADE
        )]
)
class Scratching (
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,

    var rId: Long = 0,                    // "foreign" key (_id of the Race record).
    var bettingStatus: String = "",
    var runnerName: String = "",
    var runnerNumber: Int = 0
){
}
/*
    val bettingStatus: String,    // e.g. Scratched
    val runnerName: String,       // e.g.
    val runnerNumber: Int         // e.g.
 */