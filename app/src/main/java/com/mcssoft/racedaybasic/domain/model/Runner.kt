package com.mcssoft.racedaybasic.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Runner",
    indices = [Index(value = ["raceId"])],
    foreignKeys = [
        ForeignKey(
            entity = Race::class,
            parentColumns = ["_id"],
            childColumns = ["raceId"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class Runner(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,
    var raceId: Long = 0,

    var runnerName: String = "",
    var runnerNumber: Int = -1,
    var barrierNumber: Int = -1,
    var tcdwIndicators: String = "",
    var last5Starts: String = "",
    var riderDriverName: String = "",
    var trainerName: String = "",
    var trainerFullName: String = "",
    var dfsFormRating: Int = -1,
    var handicapWeight: Double = 0.0,
    // Additional for Summary use.
    var checked: Boolean = false
)
/*
    {
      "runnerName": "STORM FORCE TEN",
      "runnerNumber": 1,
      "fixedOdds": {
        "returnWin": 3,
        "returnWinTime": "2023-09-24T03:10:50.000Z",
        "returnWinOpen": 2.9,
        "returnWinOpenDaily": 3.1,
        "returnPlace": 1.24,
        "isFavouriteWin": false,
        "isFavouritePlace": false,
        "bettingStatus": "Loser",
        "propositionNumber": 169391,
        "differential": null,
        "flucs": [
          {
            "returnWin": 3.1,
            "returnWinTime": "2023-09-24T03:02:05.000Z"
          },
          {
            "returnWin": 3,
            "returnWinTime": "2023-09-24T03:00:42.000Z"
          },
          {
            "returnWin": 2.9,
            "returnWinTime": "2023-09-24T03:00:03.000Z"
          },
          {
            "returnWin": 2.8,
            "returnWinTime": "2023-09-24T02:59:35.000Z"
          }
        ],
        "returnHistory": [
          {
            "returnWin": 3.2,
            "returnWinTime": "2023-09-23T23:44:31.546Z"
          },
          {
            "returnWin": 3.4,
            "returnWinTime": "2023-09-24T02:20:34.454Z"
          },
          {
            "returnWin": 3.5,
            "returnWinTime": "2023-09-24T02:22:05.276Z"
          },
          {
            "returnWin": 3.4,
            "returnWinTime": "2023-09-24T02:24:07.165Z"
          },
          {
            "returnWin": 3.5,
            "returnWinTime": "2023-09-24T02:30:32.266Z"
          },
          {
            "returnWin": 3.4,
            "returnWinTime": "2023-09-24T02:31:52.956Z"
          },
          {
            "returnWin": 3.6,
            "returnWinTime": "2023-09-24T02:34:35.232Z"
          },
          {
            "returnWin": 3.4,
            "returnWinTime": "2023-09-24T02:34:56.229Z"
          },
          {
            "returnWin": 3.6,
            "returnWinTime": "2023-09-24T02:35:16.254Z"
          },
          {
            "returnWin": 3.8,
            "returnWinTime": "2023-09-24T02:38:05.134Z"
          },
          {
            "returnWin": 3.9,
            "returnWinTime": "2023-09-24T02:48:48.963Z"
          },
          {
            "returnWin": 3.5,
            "returnWinTime": "2023-09-24T02:49:16.688Z"
          },
          {
            "returnWin": 3.3,
            "returnWinTime": "2023-09-24T02:50:02.351Z"
          },
          {
            "returnWin": 3.2,
            "returnWinTime": "2023-09-24T02:51:54.016Z"
          },
          {
            "returnWin": 3.1,
            "returnWinTime": "2023-09-24T02:53:37.049Z"
          },
          {
            "returnWin": 2.7,
            "returnWinTime": "2023-09-24T02:54:19.875Z"
          },
          {
            "returnWin": 2.6,
            "returnWinTime": "2023-09-24T02:56:49.686Z"
          },
          {
            "returnWin": 2.8,
            "returnWinTime": "2023-09-24T02:57:50.226Z"
          },
          {
            "returnWin": 2.7,
            "returnWinTime": "2023-09-24T02:58:20.325Z"
          },
          {
            "returnWin": 2.8,
            "returnWinTime": "2023-09-24T02:59:34.580Z"
          },
          {
            "returnWin": 2.9,
            "returnWinTime": "2023-09-24T03:00:02.828Z"
          },
          {
            "returnWin": 3,
            "returnWinTime": "2023-09-24T03:00:42.136Z"
          },
          {
            "returnWin": 3.1,
            "returnWinTime": "2023-09-24T03:02:04.934Z"
          },
          {
            "returnWin": 3,
            "returnWinTime": "2023-09-24T03:10:49.654Z"
          },
          {
            "returnWin": 2.9,
            "returnWinTime": "2023-09-23T02:38:41.829Z"
          },
          {
            "returnWin": 3.1,
            "returnWinTime": "2023-09-23T06:45:42.544Z"
          }
        ],
        "percentageChange": -3,
        "allowPlace": true
      },
      "parimutuel": {
        "returnWin": 3,
        "returnPlace": 1.3,
        "returnExact2": 0,
        "isFavouriteWin": false,
        "isFavouritePlace": true,
        "isFavouriteExact2": false,
        "bettingStatus": "Closed",
        "marketMovers": [
          {
            "returnWin": 3,
            "returnWinTime": "2023-09-24T03:16:01.000Z",
            "specialDisplayIndicator": false
          },
          {
            "returnWin": 3,
            "returnWinTime": "2023-09-24T03:14:55.000Z",
            "specialDisplayIndicator": false
          },
          {
            "returnWin": 3.1,
            "returnWinTime": "2023-09-24T03:14:07.000Z",
            "specialDisplayIndicator": false
          },
          {
            "returnWin": 3.1,
            "returnWinTime": "2023-09-24T03:13:22.000Z",
            "specialDisplayIndicator": false
          },
          {
            "returnWin": 3.6,
            "returnWinTime": "2023-09-24T02:44:36.000Z",
            "specialDisplayIndicator": true
          }
        ],
        "percentageChange": 0
      },
      "silkURL": "https://api.beta.tab.com.au/v1/tab-info-service/racing/silk/GREY%2C%20KREW%20RACING%20LOGO%2C%20RED%20ARMBANDS%20AND%20CAP%2C%20GREY%20POM%20POM",
      "trainerName": "S Kendrick",
      "vacantBox": false,
      "trainerFullName": "Stuart Kendrick",
      "barrierNumber": 3,
      "riderDriverName": "B Wheeler",
      "riderDriverFullName": "BAILEY WHEELER",
      "handicapWeight": 59,
      "harnessHandicap": null,
      "blinkers": false,
      "claimAmount": 0,
      "last5Starts": "x4362",
      "tcdwIndicators": "tc",
      "emergency": false,
      "penalty": null,
      "dfsFormRating": 89,
      "techFormRating": 0,
      "totalRatingPoints": 13,
      "earlySpeedRating": 89,
      "earlySpeedRatingBand": "LEADER",
      "_links": {
        "form": "https://api.beta.tab.com.au/v1/tab-info-service/racing/dates/2023-09-24/meetings/R/SSC/races/1/form/1?jurisdiction=QLD"
      }
    },
 */