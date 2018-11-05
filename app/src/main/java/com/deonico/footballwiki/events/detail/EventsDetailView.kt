package com.deonico.footballwiki.events.detail

import com.deonico.footballwiki.model.Event
import com.deonico.footballwiki.model.Team

interface EventsDetailView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(matchDetails: List<Event>,
                   homeTeams: List<Team>,
                   awayTeams: List<Team>)
}