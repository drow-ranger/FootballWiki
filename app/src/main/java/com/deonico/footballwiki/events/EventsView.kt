package com.deonico.footballwiki.events

import com.deonico.footballwiki.model.Event
import com.deonico.footballwiki.model.League

interface EventsView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)
    fun showLeague(data: List<League>)
}