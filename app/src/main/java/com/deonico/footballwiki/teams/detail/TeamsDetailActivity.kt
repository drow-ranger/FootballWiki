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
        supportActionBar?.hide()

        val toolbar = team_detail_toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.inflateMenu(R.menu.menu_favorite)
        menuItem  = toolbar.menu

        toolbar.setNavigationOnClickListener {
            finish()
        }

        toolbar.setOnMenuItemClickListener {
            if(it.itemId.equals(R.id.add_to_favorite)){
                if(isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                return@setOnMenuItemClickListener true
            }else{
                onOptionsItemSelected(it)
            }
        }

        team = intent.getParcelableExtra("teamData")

        supportActionBar?.title = team.strTeam

        fillData()

        setupViewPager(team_detail_viewpager)
        team_detail_tabs.setupWithViewPager(team_detail_viewpager)

        favoriteState()
        setFavorite()
    }

    private fun fillData(){
        Picasso.get().load(team.strTeamBadge).into(team_badge)
        team_name.text = team.strTeam
        team_year.text = team.intFormedYear
        team_stadium.text = team.strStadium
    }

    private fun setupViewPager(viewPager: ViewPager){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFrag(OverviewFragment.newFragment(team), "Overview")
        adapter.addFrag(PlayersFragment.newFragment(team), "Players")
        viewPager.adapter = adapter
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
            //team_detail_viewpager.snackbar("Added to favorite").show()
            Snackbar.make(team_detail_viewpager,"Added to favorite", Snackbar.LENGTH_LONG).show()
        }catch (e: SQLiteConstraintException){
            //team_detail_viewpager.snackbar(e.localizedMessage).show()
            Snackbar.make(team_detail_viewpager,e.localizedMessage, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(table.TABLE_TEAM, "(TEAM_ID = {id})", "id" to team.idTeam!!)
            }
            Snackbar.make(team_detail_viewpager,"Removed to favorite", Snackbar.LENGTH_LONG).show()
        }catch (e: SQLiteConstraintException){
            //team_detail_viewpager.snackbar(e.localizedMessage).show()
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

