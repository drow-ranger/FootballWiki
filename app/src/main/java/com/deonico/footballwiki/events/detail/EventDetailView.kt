package com.deonico.footballwiki.events.detail

import com.deonico.footballwiki.model.Event
import com.deonico.footballwiki.model.Team

interface EventDetailView{
    fun showDetail(eventDetail: List<Event>,
                   homeTeams: List<Team>,
                   awayTeams: List<Team>)
}