package com.deonico.footballwiki.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventDB(
    val id: Long?,
    val idEvent: String?,
    val dateEvent: String?,
    val strHomeTeam: String?,
    val strAwayTeam: String?,
    val intHomeScore: String?,
    val intAwayScore: String?,
    val intHomeShots: String?,
    val intAwayShots: String?,
    val strHomeGoalDetails: String?,
    val strAwayGoalDetails: String?,
    val strHomeYellowCards: String?,
    val strAwayYellowCards: String?,
    val strHomeRedCards: String?,
    val strAwayRedCards: String?,
    val idHomeTeam: String?,
    val idAwayTeam: String?
): Parcelable

{
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"
        const val EVENT_DATE: String = "EVENT_DATE"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val HOME_SHOTS: String = "HOME_SHOTS"
        const val AWAY_SHOTS: String = "AWAY_SHOTS"
        const val HOME_GOAL: String = "HOME_GOAL"
        const val AWAY_GOAL: String = "AWAY_GOAL"
        const val HOME_YELLOW: String = "HOME_YELLOW"
        const val AWAY_YELLOW: String = "AWAY_YELLOW"
        const val HOME_RED: String = "HOME_RED"
        const val AWAY_RED: String = "AWAY_RED"
        const val ID_HOME: String = "ID_HOME"
        const val ID_AWAY: String = "ID_AWAY"
    }
}