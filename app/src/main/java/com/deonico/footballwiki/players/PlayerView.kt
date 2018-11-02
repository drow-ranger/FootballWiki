package com.deonico.footballwiki.players

import com.deonico.footballwiki.model.Player

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: List<Player>)
}