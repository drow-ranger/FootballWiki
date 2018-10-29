package com.deonico.footballwiki.Events

import com.deonico.footballwiki.model.League
import com.deonico.footballwiki.model.Event

interface EventsView {
    fun showLoading()
    fun hideLoading()
    fun showList(data: List<Event>)
    fun showLeague(data: List<League>)
}