package com.deonico.footballwiki.teams

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import com.google.gson.Gson
import com.deonico.footballwiki.R
import com.deonico.footballwiki.R.color.colorAccent
import com.deonico.footballwiki.api.ApiRepository
import com.deonico.footballwiki.events.search.EventsSearchActivity
import com.deonico.footballwiki.model.League
import com.deonico.footballwiki.model.Team
import com.deonico.footballwiki.teams.detail.TeamsDetailActivity
import com.deonico.footballwiki.teams.search.TeamsSearchActivity
import com.deonico.footballwiki.util.invisible
import com.deonico.footballwiki.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamsFragment: Fragment(), AnkoComponent<Context>, TeamsView {

    private lateinit var spinner: Spinner
    private lateinit var leagueId: String

    private lateinit var listTeam: RecyclerView
    private lateinit var adapter: TeamsAdapter


    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinnerbar: LinearLayout

    private var teams: MutableList<Team> = mutableListOf()
    private var leagues: MutableList<League> = mutableListOf()

    private lateinit var presenter: TeamsPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

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

            spinnerbar = linearLayout{
                lparams(width = matchParent, height = wrapContent)
                backgroundResource = R.drawable.rounded_white_button
                spinner = spinner{}.lparams(width = matchParent)
            }

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listTeam = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                        centerVertically()
                    }
                }
            }
        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //set adapter
        adapter = TeamsAdapter(teams){
            startActivity<TeamsDetailActivity>(
                "teamData" to it
            )
        }
        listTeam.adapter = adapter

        //get data
        val request = ApiRepository()
        val gson = Gson()

        //init presenter
        presenter = TeamsPresenter(this, request, gson)
        presenter.getLeague()

        leagueId = "4331"

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val league = spinner.selectedItem as League

                leagueId = league.leagueId.orEmpty()
                if(leagueId.isNotEmpty()){
                    presenter.getTeamList(leagueId)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefresh.onRefresh {
            spinnerbar.visible()
            presenter.getTeamList(leagueId)
            progressBar.invisible()
        }
    }


    //imp mainView
    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
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

        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, leagues)
        spinner.adapter = spinnerAdapter

        spinner.setSelection(spinnerAdapter.getPosition(League("4331", "German Bundesliga")))
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.searchMenu)?.setVisible(false)
        menu?.findItem(R.id.searchMenu1)?.setVisible(true)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        menu?.findItem(R.id.searchMenu1)?.setVisible(true)
        menu?.clear()
        inflater?.inflate(
            R.menu.menu_search,
            menu
        )

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.searchMenu -> {
                val intent = Intent(context,
                    EventsSearchActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.searchMenu1 -> {
                val intent = Intent(context,
                    TeamsSearchActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
