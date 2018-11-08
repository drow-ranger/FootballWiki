package com.deonico.footballwiki.events

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.*
import com.google.gson.Gson
import com.deonico.footballwiki.R
import com.deonico.footballwiki.api.ApiRepository
import com.deonico.footballwiki.events.EventsAdapter
import com.deonico.footballwiki.events.EventsPresenter
import com.deonico.footballwiki.events.EventsView
import com.deonico.footballwiki.model.Event
import com.deonico.footballwiki.model.League
import com.deonico.footballwiki.util.invisible
import com.deonico.footballwiki.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class EventsPreviousFragment : Fragment(), AnkoComponent<Context>, EventsView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)

        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner()
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        id = R.id.listEvent
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }

    }

    private lateinit var spinner: Spinner
    private lateinit var leagueId: String


    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var events: MutableList<Event> = mutableListOf()
    private var leagues: MutableList<League> = mutableListOf()

    private lateinit var presenter: EventsPresenter
    private lateinit var adapterEvents: EventsAdapter


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //set adapter
        adapterEvents = EventsAdapter(events)
        listEvent.adapter = adapterEvents

        //get data
        val request = ApiRepository()
        val gson = Gson()

        //init presenter
        presenter = EventsPresenter(this, request, gson)
        presenter.getLeague()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val league = spinner.selectedItem as League

                leagueId = league.leagueId.orEmpty()
                if(leagueId.isNotEmpty()){
                    presenter.getPreviousEvent(leagueId)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefresh.onRefresh {
            presenter.getPreviousEvent(leagueId)
        }
    }

    override fun showEventsList(data: List<Event>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapterEvents.notifyDataSetChanged()
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLeague(data: List<League>) {
        hideLoading()
        leagues.clear()
        leagues.addAll(data)

        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, leagues)
        spinner.adapter = spinnerAdapter

        spinner.setSelection(spinnerAdapter.getPosition(League("4328", "English Premier League")))
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(
            R.menu.main_menu,
            menu
        )

        val searchView = menu?.findItem(R.id.searchMenu)?.actionView as SearchView

        searchView.setOnSearchClickListener {}
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(keyword: String?): Boolean {
                if (keyword != null) {
                    presenter.searchEvent(keyword)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        searchView.setOnCloseListener {
            progressBar.invisible()
            false
        }
    }
}
