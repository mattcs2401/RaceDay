package com.mcssoft.racedaybasic.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Race",
    indices = [Index(value = ["mtgId"])],
    foreignKeys = [
        ForeignKey(
            entity = Meeting::class,
            parentColumns = ["_id"],
            childColumns = ["mtgId"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class Race(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,
    var mtgId: Long = 0,     // "foreign" key (_id of the Meeting record).

)
/*
DTO class:
data class RaceDto(
    val _links: LinksX,
    val allIn: Boolean,
    val allowBundle: Boolean,
    val broadcastChannel: String,
    val broadcastChannels: List<String>,
    val cashOutEligibility: String,
    val fixedOddsOnlineBetting: Boolean,
    val hasFixedOdds: Boolean,
    val hasParimutuel: Boolean,
    val matchName: String,
    val meetingDate: String,
    val multiLegApproximates: List<MultiLegApproximate>,
    val raceClassConditions: String,
    val raceDistance: Int,
    val raceName: String,
    val raceNumber: Int,
    val raceStartTime: String,
    val raceStatus: String,
    val results: List<List<Int>>,
    val scratchings: List<Scratching>,
    val skyRacing: SkyRacing,
    val willHaveFixedOdds: Boolean
)
 */
