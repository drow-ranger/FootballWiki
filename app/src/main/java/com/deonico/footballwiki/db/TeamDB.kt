package com.deonico.footballwiki.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamDB(
    val id: Long?,
    val idTeam: String?,
    val strTeam: String?,
    val strStadium: String?,
    val intFormedYear: String?,
    val strDescriptionEN: String?,
    val strTeamBadge: String?

) : Parcelable {
    companion object {
        const val TABLE_TEAM: String = "TABLE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val STADIUM: String = "STADIUM"
        const val TEAM_YEAR: String = "TEAM_YEAR"
        const val DESCRIPTION: String = "DESCRIPTION"
        const val TEAM_LOGO: String = "TEAM_LOGO"
    }
}