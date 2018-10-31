package com.deonico.footballwiki.teams

import com.deonico.footballwiki.Teams.TeamsView
import com.deonico.footballwiki.api.ApiRepository
import com.deonico.footballwiki.api.TheSportDBApi
import com.deonico.footballwiki.model.LeagueResponse
import com.deonico.footballwiki.model.TeamResponse
import com.deonico.footballwiki.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamsPresenter(private val view: TeamsView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getLeague(){
        view.showLoading()

        val api = TheSportDBApi.getListLeague()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(api),
                    LeagueResponse::class.java)
            }

            view.showLeague(data.await().countrys)
            view.hideLoading()
        }

    }

    fun getTeamList(leagueId: String?){
        view.showLoading()

        val api = TheSportDBApi.getAllTeams(leagueId)

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(api),
                    TeamResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }

}