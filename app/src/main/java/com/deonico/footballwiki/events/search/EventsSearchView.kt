package com.deonico.footballwiki.events.search

import com.deonico.footballwiki.model.Event

interface EventsSearchView {
    fun showLoading()
    fun hideLoading()
    fun showList(data: List<Event>)
}