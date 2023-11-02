package com.mcssoft.raceday.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Race",
    indices = [
        Index(
            value = ["mtgId", "venueMnemonic", "raceNumber"],
            unique = true
        )],
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
    var venueMnemonic: String,         // venue code.

    var raceNumber: Int,               // e.g. 1
    var raceClassConditions: String?,  // e.g. "3YO MDN"
    var raceName: String,              // e.g. "THE LAKEHOUSE QTIS 3YO MAIDEN HANDICAP"
    var raceStartTime: String,         // e.g. "2023-09-24T03:10:00.000Z"
    var raceDistance: Int,             // e.g. 1600
    var raceStatus: String,            // for anything Abandoned.
    var hasScratchings: Boolean        // flag for Scratchings exist.
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
