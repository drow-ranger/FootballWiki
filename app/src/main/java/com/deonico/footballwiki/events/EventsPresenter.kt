package com.deonico.footballwiki.events

import android.util.Log
import com.deonico.footballwiki.api.ApiRepository
import com.deonico.footballwiki.api.TheSportDBApi
import com.deonico.footballwiki.model.EventResponse
import com.deonico.footballwiki.model.LeagueResponse
import com.deonico.footballwiki.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class EventsPresenter(private val view: EventsView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getLeague(){
        view.showLoading()

        val api = TheSportDBApi.getListLeague()

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository.
                    doRequest(api),
                    LeagueResponse::class.java)
            }

            view.showLeague(data.await().countrys)
            view.hideLoading()
        }
    }

    fun getPreviousEvent(leagueId: String) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getPreviousEvent(leagueId)),
                    EventResponse::class.java
                )
            }

            view.hideLoading()
            view.showEventsList(data.await().events)
        }
    }

    fun searchEvent(keyword: String) {

        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getEvents(keyword)),
                    EventResponse::class.java
                )
            }

            view.hideLoading()
            view.showEventsList(data.await().events)
        }
    }

}