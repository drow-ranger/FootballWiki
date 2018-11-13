package com.deonico.footballwiki.events

import com.deonico.footballwiki.TestContextProvider
import com.deonico.footballwiki.api.ApiRepository
import com.deonico.footballwiki.api.TheSportDBApi
import com.deonico.footballwiki.model.Event
import com.deonico.footballwiki.model.EventResponse
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class EventsPresenterTest {

    @Mock
    private lateinit var viewTV: EventsView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    private lateinit var presenterTV: EventsPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenterTV = EventsPresenter(viewTV, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getNextEvent() {
        
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events, events)
        val leagueId = "4331"
        //Testing response get data from server api
        `when`(
            gson.fromJson(
                apiRepository
                    .doRequest(TheSportDBApi.getNextEvent(leagueId)),
                EventResponse::class.java
            )
        ).thenReturn(response)

        //Testing get data
        presenterTV.getNextEvent(leagueId)

        Mockito.verify(viewTV).showLoading()
        Mockito.verify(viewTV).showEventList(events)
        Mockito.verify(viewTV).hideLoading()

    }
}