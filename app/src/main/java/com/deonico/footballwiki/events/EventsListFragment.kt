package com.deonico.footballwiki.events

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.google.gson.Gson
import com.deonico.footballwiki.R
import com.deonico.footballwiki.api.ApiRepository
import com.deonico.footballwiki.model.Event
import com.deonico.footballwiki.model.League
import com.deonico.footballwiki.events.detail.EventsDetailActivity

import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class EventsListFragment: Fragment(), AnkoComponent<Context>, EventsListView{
    private val events: MutableList<Event> = mutableListOf()
    private val leagues: MutableList<League> = mutableListOf()
    private lateinit var presenter: EventsListPresenter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var matchesList: RecyclerView
    private lateinit var adapter: EventsListAdapter
    private lateinit var spinner: Spinner
    private var fixture = 1
    private var leagueId = "4328"   //EPL


    companion object {
        fun newFragment(fixture: Int, leagueId: String): EventsListFragment{
            val fragment = EventsListFragment()
            fragment.fixture = fixture
            fragment.leagueId = leagueId

            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val request = ApiRepository()
        val gson = Gson()
        presenter = EventsListPresenter(this, request, gson, fixture)
        presenter.getLeague()

        adapter = EventsListAdapter(events){
            startActivity<EventsDetailActivity>("EVENT" to it)
        }

        matchesList.adapter = adapter

        swipeRefresh.onRefresh {
            presenter.getEvent(leagueId)
        }

//        presenter.getList(leagueId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(context!!))
    }

    override fun createView(ui: AnkoContext<Context>) = with(ui){
        linearLayout {
            orientation = LinearLayout.VERTICAL
            lparams(width = matchParent, height = matchParent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner {
                id = R.id.sp_league

                onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val league = spinner.selectedItem as League

                        leagueId = league.leagueId.orEmpty()
                        if(leagueId.isNotEmpty()){
                            presenter.getEvent(leagueId)
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
            }.lparams(width = matchParent, height = wrapContent)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                matchesList = recyclerView {
                    id = R.id.rv_matches_list
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }.lparams(width = matchParent, height = matchParent)
        }
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun showLeague(data: List<League>) {
        hideLoading()
        leagues.clear()
        leagues.addAll(data)

        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, leagues)
        spinner.adapter = spinnerAdapter

        spinner.setSelection(spinnerAdapter.getPosition(League("4328", "English Premier League")))
    }

    override fun showList(data: List<Event>) {
        hideLoading()
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

}