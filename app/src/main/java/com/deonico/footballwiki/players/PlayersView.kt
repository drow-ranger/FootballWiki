package com.deonico.footballwiki.players

import com.deonico.footballwiki.model.Player

interface PlayersView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: List<Player>)
}