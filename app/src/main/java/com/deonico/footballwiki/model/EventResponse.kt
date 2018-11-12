package com.deonico.footballwiki.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Event(
    @SerializedName("idEvent") val idEvent: String? = null,
    @SerializedName("idSoccerXML") val idSoccerXML: String? = null,
    @SerializedName("strEvent") val strEvent : String? = null,
    @SerializedName("strFilename") val strFilename : String? = null,
    @SerializedName("strSport") val strSport : String? = null,
    @SerializedName("idLeague") val idLeague: String? = null,
    @SerializedName("strLeague") val strLeague : String? = null,
    @SerializedName("strSeason") val strSeason: String? = null,
    @SerializedName("strDescriptionEN") val strDescriptionEN : String? = null,
    @SerializedName("strHomeTeam") val strHomeTeam : String? = null,
    @SerializedName("strAwayTeam") val strAwayTeam : String? = null,
    @SerializedName("intHomeScore") val intHomeScore : String? = null,
    @SerializedName("intRound") val intRound: String? = null,
    @SerializedName("intAwayScore") val intAwayScore : String? = null,
    @SerializedName("intSpectators") val intSpectators : String? = null,
    @SerializedName("strHomeGoalDetails") val strHomeGoalDetails : String? = null,
    @SerializedName("strHomeRedCards") val strHomeRedCards : String? = null,
    @SerializedName("strHomeYellowCards") val strHomeYellowCards : String? = null,
    @SerializedName("strHomeLineupGoalkeeper") val strHomeLineupGoalkeeper : String? = null,
    @SerializedName("strHomeLineupDefense") val strHomeLineupDefense : String? = null,
    @SerializedName("strHomeLineupMidfield") val strHomeLineupMidfield : String? = null,
    @SerializedName("strHomeLineupForward") val strHomeLineupForward : String? = null,
    @SerializedName("strHomeLineupSubstitutes") val strHomeLineupSubstitutes : String? = null,
    @SerializedName("strHomeFormation") val strHomeFormation : String? = null,
    @SerializedName("strAwayRedCards") val strAwayRedCards : String? = null,
    @SerializedName("strAwayYellowCards") val strAwayYellowCards : String? = null,
    @SerializedName("strAwayGoalDetails") val strAwayGoalDetails : String? = null,
    @SerializedName("strAwayLineupGoalkeeper") val strAwayLineupGoalkeeper : String? = null,
    @SerializedName("strAwayLineupDefense") val strAwayLineupDefense : String? = null,
    @SerializedName("strAwayLineupMidfield") val strAwayLineupMidfield : String? = null,
    @SerializedName("strAwayLineupForward") val strAwayLineupForward : String? = null,
    @SerializedName("strAwayLineupSubstitutes") val strAwayLineupSubstitutes : String? = null,
    @SerializedName("strAwayFormation") val strAwayFormation : String? = null,
    @SerializedName("intHomeShots") val intHomeShots : String? = null,
    @SerializedName("intAwayShots") val intAwayShots : String? = null,
    @SerializedName("dateEvent") val dateEvent : String? = null,
    @SerializedName("strDate") val strDate : String? = null,
    @SerializedName("strTime") val strTime : String? = null,
    @SerializedName("strTVStation") val strTVStation : String? = null,
    @SerializedName("idHomeTeam") val idHomeTeam: String? = null,
    @SerializedName("idAwayTeam") val idAwayTeam: String? = null,
    @SerializedName("strResult") val strResult : String? = null,
    @SerializedName("strCircuit") val strCircuit : String? = null,
    @SerializedName("strCountry") val strCountry : String? = null,
    @SerializedName("strCity") val strCity : String? = null,
    @SerializedName("strPoster") val strPoster : String? = null,
    @SerializedName("strFanart") val strFanart : String? = null,
    @SerializedName("strThumb") val strThumb : String? = null,
    @SerializedName("strBanner") val strBanner : String? = null,
    @SerializedName("strMap") val strMap : String? = null,
    @SerializedName("strLocked") val strLocked : String? = null
)
    :Parcelable



data class EventResponse(
    val events: List<Event>, val event: List<Event>
)