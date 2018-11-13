package com.deonico.footballwiki.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventDB(
    val id: Long?,
    var idEvent: String?,
    var strEvent: String?,
    var strFilename: String?,
    var dateEvent: String?,
    var strTime: String?,
    var idHomeTeam: String?,
    var strHomeTeam: String?,
    var intHomeScore: String?,
    var idAwayTeam: String?,
    var strAwayTeam: String?,
    var intAwayScore: String?): Parcelable {

    companion object {
        const val TABLE_EVENT = "TABLE_EVENT"
        const val ID = "ID_"
        const val EVENT_ID = "EVENT_ID"
        const val EVENT_NAME = "EVENT_NAME"
        const val EVENT_FILENAME = "EVENT_FILENAME"
        const val EVENT_DATE = "EVENT_DATE"
        const val EVENT_TIME = "EVENT_TIME"
        const val HOME_TEAM_ID = "HOME_TEAM_ID"
        const val HOME_TEAM_NAME = "HOME_TEAM_NAME"
        const val HOME_TEAM_SCORE = "HOME_TEAM_SCORE"
        const val AWAY_TEAM_ID = "AWAY_TEAM_ID"
        const val AWAY_TEAM_NAME = "AWAY_TEAM_NAME"
        const val AWAY_TEAM_SCORE = "AWAY_TEAM_SCORE"
    }
}