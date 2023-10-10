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
    var mtgId: Long,                   // "foreign" key (_id of the Meeting record).

    var raceNo: Int,                   // e.g. 1
    var raceClassConditions: String,   // e.g. "3YO MDN"
    var raceName: String,              // e.g. "THE LAKEHOUSE QTIS 3YO MAIDEN HANDICAP"
    var raceStartTime: String,         // e.g. "2023-09-24T03:10:00.000Z"
    var raceDistance: Int,             // e.g. 1600
    var raceStatus: String,            // for anything Abandoned.
    var runnersBaseUrl: String?
)
// *** Note: This is only the basic "view". ***
// More detail via this (as example):
// https://api.beta.tab.com.au/v1/tab-info-service/racing/dates/2023-09-24/meetings/R/SSC/races?jurisdiction=QLD
/*  JSON
      "races": [
        {
          "raceNumber": 1,
          "raceClassConditions": "3YO MDN",
          "raceName": "THE LAKEHOUSE QTIS 3YO MAIDEN HANDICAP",
          "raceStartTime": "2023-09-24T03:10:00.000Z",
          "raceStatus": "Paying",
          "raceDistance": 1600,
          "hasParimutuel": true,
          "hasFixedOdds": false,
          "broadcastChannel": "Sky Racing 1",
          "broadcastChannels": [
            "Sky Racing 1"
          ],
          "skyRacing": {
            "audio": "https://mediatabs.skyracing.com.au/Audio_Replay/2023/09/20230924SUNR01.mp3",
            "video": "https://mediatabs.skyracing.com.au/Race_Replay/2023/09/20230924SUNR01_V.mp4"
          },
          "willHaveFixedOdds": true,
          "allIn": false,
          "cashOutEligibility": "Disabled",
          "allowBundle": false,
          "results": [
            [6],
            [8],
            [5],
            [2]
          ],
          "scratchings": [
            {
              "runnerNumber": 3,
              "runnerName": "VICTORY ROLL",
              "bettingStatus": "Scratched"
            }
          ]
        },
 */
/*
DTO class:
data class RaceDto(
    val _links: LinksXDto,                     //
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
