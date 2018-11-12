package com.deonico.footballwiki.players

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.deonico.footballwiki.R
import com.deonico.footballwiki.api.ApiRepository
import com.deonico.footballwiki.model.Player
import com.deonico.footballwiki.model.Team
import com.deonico.footballwiki.players.detail.PlayersAdapter
import com.deonico.footballwiki.players.detail.PlayersDetailActivity
import com.deonico.footballwiki.util.invisible
import com.deonico.footballwiki.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PlayersFragment : Fragment(), AnkoComponent<Context>, PlayersView {

    private lateinit var team: Team
    private lateinit var listPlayer: RecyclerView
    private lateinit var adapter: PlayersAdapter


    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var players: MutableList<Player> = mutableListOf()

    private lateinit var presenter: PlayersPresenter

    private var parameter: String? = null

    companion object {
        fun newFragment(team: Team): PlayersFragment {
            val fragment = PlayersFragment()
            fragment.team = team

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listPlayer = recyclerView {
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //parameter = getArguments()?.getString("parameter")

        //set adapter
        adapter = PlayersAdapter(players){
            startActivity<PlayersDetailActivity>(
                "playerData" to it
            )
        }
        listPlayer.adapter = adapter

        //get data
        val request = ApiRepository()
        val gson = Gson()

        //init presenter
        presenter = PlayersPresenter(this, request, gson)
        presenter.getPlayerList(team.idTeam)


        swipeRefresh.onRefresh {
            presenter.getPlayerList(team.idTeam)
        }
    }


    override fun showPlayerList(data: List<Player>) {
        swipeRefresh.isRefreshing = false
        players.clear()
        players.addAll(data)
        adapter.notifyDataSetChanged()
    }


    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }


}

