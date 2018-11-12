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
import com.deonico.footballwiki.db.PlayerDB
import com.deonico.footballwiki.db.database
import com.deonico.footballwiki.model.Player
import com.deonico.footballwiki.players.detail.PlayersDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoritePlayersFragment: Fragment(), AnkoComponent<Context> {

    private var players: MutableList<PlayerDB> = mutableListOf()
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var playerList: RecyclerView
    private lateinit var adapter: FavoritePlayersAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        adapter = FavoritePlayersAdapter(players){
            val player = Player(
                idPlayer = it.idPlayer,
                strThumb = it.strThumb,
                strPlayer = it.strPlayer,
                strPosition = it.strPosition,
                strDescriptionEN = it.strDescriptionEN
            )

            requireContext().startActivity<PlayersDetailActivity>("playerData" to player)
        }
        playerList.adapter = adapter

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

                playerList = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }

        }
    }

    private fun showFavorite(){
        requireContext().database.use {
            swipeRefresh.isRefreshing = true
            val result = select(PlayerDB.TABLE_PLAYER)
            val match = result.parseList(classParser<PlayerDB>())
            players.clear()
            players.addAll(match)
            adapter.notifyDataSetChanged()
            swipeRefresh.isRefreshing = false
        }
    }
}