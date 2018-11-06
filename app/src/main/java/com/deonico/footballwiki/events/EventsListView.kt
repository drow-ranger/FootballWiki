package com.deonico.footballwiki.events

import com.deonico.footballwiki.model.Event
import com.deonico.footballwiki.model.League

interface EventsListView {
    fun showLoading()
    fun hideLoading()
    fun showList(data: List<Event>)
    fun showLeague(data: List<League>)
}