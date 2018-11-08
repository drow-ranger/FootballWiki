package com.deonico.footballwiki.events

import com.deonico.footballwiki.api.ApiRepository
import com.deonico.footballwiki.api.TheSportDBApi
import com.deonico.footballwiki.model.EventResponse
import com.deonico.footballwiki.model.LeagueResponse
import com.deonico.footballwiki.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class EventsListPresenter(private val view: EventsListView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson,
                          private val fixture: Int = 1,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()){

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

    fun getEvent(id: String?){
        view.showLoading()

        val api = if (fixture == 1) TheSportDBApi.getPreviousEvent(id)
        else TheSportDBApi.getNextEvent(id)

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository.
                    doRequest(api),
                    EventResponse::class.java)
            }

            view.showList(data.await().events)
            view.hideLoading()
        }
    }
}