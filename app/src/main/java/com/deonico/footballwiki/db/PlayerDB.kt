package com.deonico.footballwiki.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayerDB (
    val id: Long?,
    val idPlayer: String?,
    val strPlayer: String,
    val strPosition: String,
    val strThumb: String,
    val strDescriptionEN: String

) : Parcelable{
    companion object {
        const val TABLE_PLAYER: String = "TABLE_PLAYER"
        const val ID: String = "ID_"
        const val PLAYER_ID: String = "PLAYER_ID"
        const val PLAYER_NAME: String = "PLAYER_NAME"
        const val PLAYER_POSITION: String = "PLAYER_POSITION"
        const val PLAYER_THUMB: String = "PLAYER_THUMB"
        const val DESCRIPTION: String = "DESCRIPTION"
    }
}

