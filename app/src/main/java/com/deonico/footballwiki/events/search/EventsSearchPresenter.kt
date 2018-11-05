package com.deonico.footballwiki.events.search

import com.deonico.footballwiki.api.ApiRepository
import com.deonico.footballwiki.api.TheSportDBApi
import com.deonico.footballwiki.model.EventResponse
import com.deonico.footballwiki.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class EventsSearchPresenter(private val view: EventsSearchView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()){

    fun getEvent(query: String?){
        view.showLoading()

        val api = TheSportDBApi.getEvents(query)

        async(context.main){
            val data = bg {
                gson.fromJson(apiRepository.
                    doRequest(api),
                    EventResponse::class.java)
            }

            view.showList(data.await().event)
            view.hideLoading()
        }
    }

}