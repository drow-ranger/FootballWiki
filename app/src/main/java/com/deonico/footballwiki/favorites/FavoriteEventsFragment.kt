package com.deonico.footballwiki.favorites

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.deonico.footballwiki.R
import com.deonico.footballwiki.db.EventDB
import com.deonico.footballwiki.db.database
import com.deonico.footballwiki.events.detail.EventsDetailActivity
import com.deonico.footballwiki.model.Event
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteEventsFragment: Fragment(), AnkoComponent<Context> {

    private var events: MutableList<EventDB> = mutableListOf()
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var eventList: RecyclerView
    private lateinit var adapter: FavoriteEventsAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteEventsAdapter(events){
            val event = Event(
                idEvent = it.idEvent,
                strEvent = it.strEvent,
                strFilename = it.strFilename,
                dateEvent = it.dateEvent,
                strTime = it.strTime,
                idHomeTeam = it.idHomeTeam,
                strHomeTeam = it.strHomeTeam,
                intHomeScore = it.intHomeScore,
                idAwayTeam = it.idAwayTeam,
                strAwayTeam = it.strAwayTeam,
                intAwayScore = it.intAwayScore
            )

            requireContext().startActivity<EventsDetailActivity>("eventData" to event)
        }
        eventList.adapter = adapter

        showFavorite()
        swipeRefresh.onRefresh {
            showFavorite()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)

        return createView(AnkoContext.create(requireContext()))
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.searchMenu)?.setVisible(false)
        super.onPrepareOptionsMenu(menu)
    }

    override fun createView(ui: AnkoContext<Context>) = with(ui){
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)
            orientation = LinearLayout.VERTICAL

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                eventList = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }

        }
    }

    private fun showFavorite(){
        requireContext().database.use {
            swipeRefresh.isRefreshing = true
            val result = select(EventDB.TABLE_MATCH)
            val match = result.parseList(classParser<EventDB>())
            events.clear()
            events.addAll(match)
            adapter.notifyDataSetChanged()
            swipeRefresh.isRefreshing = false
        }
    }
}