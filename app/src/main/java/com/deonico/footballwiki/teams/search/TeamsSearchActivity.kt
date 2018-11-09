package com.deonico.footballwiki.teams.search

import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.LinearLayout
import com.deonico.footballwiki.R
import com.deonico.footballwiki.api.ApiRepository
import com.deonico.footballwiki.teams.detail.TeamDetailActivity
import com.deonico.footballwiki.model.Team
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamsSearchActivity: AppCompatActivity(), TeamsSearchView{
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var searchView: SearchView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var matchesList: RecyclerView
    private lateinit var presenter: TeamsSearchPresenter
    private lateinit var adapter: TeamsSearchAdapter
    private var query: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamsSearchPresenter(this, request, gson)

        adapter = TeamsSearchAdapter(teams){
            startActivity<TeamDetailActivity>(
                "teamObject" to it
            )
        }

        linearLayout{
            lparams(width = matchParent, height = matchParent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

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

        matchesList.adapter = adapter

        swipeRefresh.onRefresh {
            presenter.getTeam(query)
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_view, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setIconifiedByDefault(false)
        searchView.isIconified = false
        searchView.requestFocusFromTouch()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                if(text?.length!! > 2) {
                    query = text
                    presenter.getTeam(query)
                }
                return false
            }

        })

        return true
    }*/

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun showList(data: List<Team>) {
        hideLoading()

        teams.clear()
        data.forEach {
            if(it.strSport.equals("Soccer")){
                teams.add(it)
            }
        }

//        events.addAll(data)
        adapter.notifyDataSetChanged()
    }
}