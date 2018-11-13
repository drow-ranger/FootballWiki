package com.deonico.footballwiki.teams.detail

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.deonico.footballwiki.R
import com.deonico.footballwiki.db.TeamDB
import com.deonico.footballwiki.db.database
import com.deonico.footballwiki.model.Team
import com.deonico.footballwiki.players.PlayersFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class TeamsDetailActivity: AppCompatActivity(){
    private lateinit var team: Team
    private val table = TeamDB

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        team = intent.getParcelableExtra("teamData")


        fillData()

        setupViewPager(team_detail_viewpager)
        team_detail_tabs.setupWithViewPager(team_detail_viewpager)

        favoriteState()
    }

    private fun fillData(){
        Picasso.get().load(team.strTeamBadge).into(iv_icon)
        tv_name.text = team.strTeam
        tv_year.text = team.intFormedYear
        tv_stadium.text = team.strStadium
        supportActionBar?.title = team.strTeam
    }

    private fun setupViewPager(viewPager: ViewPager){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFrag(OverviewFragment.newFragment(team), "OVERVIEW")
        adapter.addFrag(PlayersFragment.newFragment(team), "PLAYERS")
        viewPager.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuItem = menu
        setFavorite()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if(isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try{
            database.use {
                insert(table.TABLE_TEAM,
                    table.TEAM_ID to team.idTeam,
                    table.TEAM_NAME to team.strTeam,
                    table.TEAM_LOGO to team.strTeamBadge,
                    table.STADIUM to team.strStadium,
                    table.TEAM_YEAR to team.intFormedYear,
                    table.DESCRIPTION to team.strDescriptionEN)
            }
            Snackbar.make(team_detail_viewpager,R.string.add_favorite, Snackbar.LENGTH_LONG).show()
        }catch (e: SQLiteConstraintException){
            Snackbar.make(team_detail_viewpager,e.localizedMessage, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(table.TABLE_TEAM, "(TEAM_ID = {id})", "id" to team.idTeam!!)
            }
            Snackbar.make(team_detail_viewpager,R.string.remove_favorite, Snackbar.LENGTH_LONG).show()
        }catch (e: SQLiteConstraintException){
            Snackbar.make(team_detail_viewpager,e.localizedMessage, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setFavorite(){
        if (isFavorite) menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorite)
        else menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorite)
    }

    private fun favoriteState(){
        database.use {
            val result = select(table.TABLE_TEAM)
                .whereArgs("(TEAM_ID = {id})",
                    "id" to team.idTeam!!)
            val favorite = result.parseList(classParser<TeamDB>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    class ViewPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager) {
        private val mFragmentList: MutableList<Fragment> = mutableListOf()
        private val mFragmentTitleList: MutableList<String> = mutableListOf()

        override fun getItem(position: Int) = mFragmentList.get(position)

        override fun getCount() = mFragmentList.size

        fun addFrag(fragment: Fragment, title: String){
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int) = mFragmentTitleList.get(position)
    }

}

