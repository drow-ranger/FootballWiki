package com.deonico.footballwiki.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player(
    @SerializedName("idPlayer") val idPlayer : Int,
    @SerializedName("idTeam") val idTeam : Int,
    @SerializedName("idSoccerXML") val idSoccerXML : Int,
    @SerializedName("idPlayerManager") val idPlayerManager : Int,
    @SerializedName("strNationality") val strNationality : String? = null,
    @SerializedName("strPlayer") val strPlayer : String? = null,
    @SerializedName("strTeam") val strTeam : String? = null,
    @SerializedName("strSport") val strSport : String? = null,
    @SerializedName("intSoccerXMLTeamID") val intSoccerXMLTeamID : Int,
    @SerializedName("dateBorn") val dateBorn : String? = null,
    @SerializedName("dateSigned") val dateSigned : String? = null,
    @SerializedName("strSigning") val strSigning : String? = null,
    @SerializedName("strWage") val strWage : String? = null,
    @SerializedName("strBirthLocation") val strBirthLocation : String? = null,
    @SerializedName("strDescriptionEN") val strDescriptionEN : String? = null,
    @SerializedName("strDescriptionDE") val strDescriptionDE : String? = null,
    @SerializedName("strDescriptionFR") val strDescriptionFR : String? = null,
    @SerializedName("strDescriptionCN") val strDescriptionCN : String? = null,
    @SerializedName("strDescriptionIT") val strDescriptionIT : String? = null,
    @SerializedName("strDescriptionJP") val strDescriptionJP : String? = null,
    @SerializedName("strDescriptionRU") val strDescriptionRU : String? = null,
    @SerializedName("strDescriptionES") val strDescriptionES : String? = null,
    @SerializedName("strDescriptionPT") val strDescriptionPT : String? = null,
    @SerializedName("strDescriptionSE") val strDescriptionSE : String? = null,
    @SerializedName("strDescriptionNL") val strDescriptionNL : String? = null,
    @SerializedName("strDescriptionHU") val strDescriptionHU : String? = null,
    @SerializedName("strDescriptionNO") val strDescriptionNO : String? = null,
    @SerializedName("strDescriptionIL") val strDescriptionIL : String? = null,
    @SerializedName("strDescriptionPL") val strDescriptionPL : String? = null,
    @SerializedName("strGender") val strGender : String? = null,
    @SerializedName("strPosition") val strPosition : String? = null,
    @SerializedName("strCollege") val strCollege : String? = null,
    @SerializedName("strFacebook") val strFacebook : String? = null,
    @SerializedName("strWebsite") val strWebsite : String? = null,
    @SerializedName("strTwitter") val strTwitter : String? = null,
    @SerializedName("strInstagram") val strInstagram : String? = null,
    @SerializedName("strYoutube") val strYoutube : String? = null,
    @SerializedName("strHeight") val strHeight : Double,
    @SerializedName("strWeight") val strWeight : Double,
    @SerializedName("intLoved") val intLoved : Int,
    @SerializedName("strThumb") val strThumb : String? = null,
    @SerializedName("strCutout") val strCutout : String? = null,
    @SerializedName("strBanner") val strBanner : String? = null,
    @SerializedName("strFanart1") val strFanart1 : String? = null,
    @SerializedName("strFanart2") val strFanart2 : String? = null,
    @SerializedName("strFanart3") val strFanart3 : String? = null,
    @SerializedName("strFanart4") val strFanart4 : String? = null,
    @SerializedName("strLocked") val strLocked : String
)
    :Parcelable

data class PlayerResponse(
    @SerializedName("player") val player: List<Player>
)