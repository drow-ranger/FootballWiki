package com.deonico.footballwiki.Teams

import com.deonico.footballwiki.model.League
import com.deonico.footballwiki.model.Team

interface TeamsView {

    fun showLoading()
    fun hideLoading()
    fun showLeague(data: List<League>)
    fun showTeamList(data: List<Team>)

}