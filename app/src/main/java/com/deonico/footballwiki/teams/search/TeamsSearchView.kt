package com.deonico.footballwiki.teams.search

import com.deonico.footballwiki.model.Team

interface TeamsSearchView {
    fun showLoading()
    fun hideLoading()
    fun showList(data: List<Team>)
}