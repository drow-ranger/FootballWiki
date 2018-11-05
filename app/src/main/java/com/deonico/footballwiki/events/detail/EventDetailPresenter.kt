package com.deonico.footballwiki.events.detail

import com.deonico.footballwiki.api.ApiRepository
import com.deonico.footballwiki.api.TheSportDBApi
import com.deonico.footballwiki.model.EventResponse
import com.deonico.footballwiki.model.TeamResponse
import com.deonico.footballwiki.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class EventDetailPresenter(private val view: EventDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getEventDetail(eventId: String?, homeTeamId: String?, awayTeamId: String?){

        async(context.main) {
            async(context.main) {
                val matchDetail = bg {
                    gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getMatchDetail(eventId)),
                        EventResponse::class.java)
                }
                val homeTeam = bg {
                    gson.fromJson(ApiRepository()
                        .doRequest(TheSportDBApi.getTeamDetail(homeTeamId)),
                        TeamResponse::class.java)
                }
                val awayTeam = bg {
                    gson.fromJson(ApiRepository()
                        .doRequest(TheSportDBApi.getTeamDetail(awayTeamId)),
                        TeamResponse::class.java)
                }

                view.showDetail(
                    matchDetail.await().events,
                    homeTeam.await().teams,
                    awayTeam.await().teams)
            }
        }
    }

}