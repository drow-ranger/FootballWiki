package com.deonico.footballwiki.teams.search

import com.deonico.footballwiki.api.ApiRepository
import com.deonico.footballwiki.api.TheSportDBApi
import com.deonico.footballwiki.model.TeamResponse
import com.deonico.footballwiki.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamsSearchPresenter(private val view: TeamsSearchView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()
){

    fun getTeam(keyword: String) {

        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getTeams(keyword)),
                    TeamResponse::class.java
                )
            }

            view.hideLoading()
            view.showList(data.await().teams)
        }
    }

}