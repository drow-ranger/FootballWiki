package com.deonico.footballwiki.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
/*

@Parcelize
data class EventDB(
    val id: Long?,
    val idEvent: String?,
    val dateEvent: String?,*/
/*
    val timeEvent: String?,*//*

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
        const val TABLE_EVENT: String = "TABLE_EVENT"
        const val ID: String = "ID_"
        const val EVENT_ID: String = "EVENT_ID"*/
/*
        const val EVENT_NAME: String = "EVENT_NAME"*//*

        const val EVENT_DATE: String = "EVENT_DATE"*/
/*
        const val EVENT_TIME: String = "EVENT_TIME"
        const val HOME_LOGO: String = "HOME_LOGO"
        const val AWAY_LOGO: String = "AWAY_LOGO"*//*

        const val HOME_TEAM: String = "HOME_TEAM"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        */
/*const val HOME_GOAL: String = "HOME_GOAL"
        const val AWAY_GOAL: String = "AWAY_GOAL"
        const val HOME_GK: String = "HOME_GK"
        const val AWAY_GK: String = "AWAY_GK"
        const val HOME_DEF: String = "HOME_DEF"
        const val AWAY_DEF: String = "AWAY_DEF"
        const val HOME_MDF: String = "HOME_MDF"
        const val AWAY_MDF: String = "AWAY_MDF"
        const val HOME_FW: String = "HOME_FW"
        const val AWAY_FW: String = "AWAY_FW"
        const val HOME_SUB: String = "HOME_SUB"
        const val AWAY_SUB: String = "AWAY_SUB"*//*

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
}*/


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
        const val TABLE_MATCH = "TABLE_MATCH"
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