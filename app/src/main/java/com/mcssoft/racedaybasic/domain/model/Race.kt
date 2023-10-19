package com.mcssoft.racedaybasic.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Race",
    indices = [Index(value = ["mtgId", "venue", "raceNo"])],
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
    var mtgId: Long,                   // "foreign" key (_id of the Meeting record).
    var venue: String,                 // venue code.

    var raceNo: Int,                   // e.g. 1
    var raceClassConditions: String?,  // e.g. "3YO MDN"
    var raceName: String,              // e.g. "THE LAKEHOUSE QTIS 3YO MAIDEN HANDICAP"
    var raceStartTime: String,         // e.g. "2023-09-24T03:10:00.000Z"
    var raceDistance: Int,             // e.g. 1600
    var raceStatus: String,            // for anything Abandoned.
    var runnersBaseUrl: String?        // TBA
)
/*
DTO class:
data class RaceDto(
    val _links: LinksXDto,                  //
    val allIn: Boolean,                     // ignore
    val allowBundle: Boolean,               // ignore
    val broadcastChannel: String,           // ignore
    val broadcastChannels: List<String>,    // ignore
    val cashOutEligibility: String,         // ignore
    val fixedOddsOnlineBetting: Boolean,    // ignore
    val hasFixedOdds: Boolean,              // ignore
    val hasParimutuel: Boolean,             // ignore
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
    val scratchings: List<ScratchingDto>,
    val skyRacing: SkyRacing,
    val willHaveFixedOdds: Boolean
)
 */
