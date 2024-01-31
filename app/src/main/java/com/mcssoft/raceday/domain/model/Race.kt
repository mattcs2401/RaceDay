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
        )
    ],
    foreignKeys = [
        ForeignKey(
            entity = Meeting::class,
            parentColumns = ["id"],
            childColumns = ["mtgId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Race(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var mtgId: Long,

    var sellCode: String,
    var venueMnemonic: String,

    var raceNumber: Int,
    var raceClassConditions: String?,
    var raceName: String,
    var raceStartTime: String,
    var raceDistance: Int,
    var raceStatus: String,
    var hasScratchings: Boolean
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
