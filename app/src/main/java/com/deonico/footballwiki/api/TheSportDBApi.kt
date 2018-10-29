package com.deonico.footballwiki.api

import com.deonico.footballwiki.BuildConfig

object TheSportDBApi {

    private fun urlBuild(path: String, id: String? = null): String{
        val url = BuildConfig.BASE_URL+"api/v1/json/"+BuildConfig.TSDB_API_KEY+"/"+path
        return if(id.isNullOrEmpty()) url else url+"?id="+id
    }

    fun getListLeague() = urlBuild("search_all_leagues.php?s=Soccer")
    fun getPrevSchedule(id: String?) = urlBuild("eventspastleague.php", id)
    fun getNextSchedule(id: String?) = urlBuild("eventsnextleague.php", id)
    fun getMatchDetail(id: String?) = urlBuild("lookupevent.php", id)
    fun getAllTeams(id: String?) = urlBuild("lookup_all_teams.php", id)
    fun getTeamDetail(id: String?) = urlBuild("lookupteam.php", id)
    fun getPlayers(id: String?) = urlBuild("lookup_all_players.php", id)
    fun getPlayerDetail(id: String?) = urlBuild("lookupplayer.php", id)
    fun getTeams(keywords: String?) = urlBuild("searchteams.php?t="+keywords)
    fun getEvents(keywords: String?) = urlBuild("searchevents.php?e="+keywords)
}