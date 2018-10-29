package com.deonico.footballwiki.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League (
    @SerializedName("idLeague")
    var leagueId: String? = null,

    @SerializedName("strLeague")
    var leagueName: String? = null
)
    : Parcelable{
    override fun toString(): String {
        return leagueName.orEmpty()
    }
}

data class LeagueResponse(
    val countrys: List<League>
)

