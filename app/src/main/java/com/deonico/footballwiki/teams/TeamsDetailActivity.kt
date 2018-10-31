package com.deonico.footballwiki.teams

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.deonico.footballwiki.R
import com.deonico.footballwiki.model.Team
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class TeamsDetailActivity : AppCompatActivity() {

    private lateinit var dataTeamId: String

    //private lateinit var favorit: FavoriteTeam

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val dataTeam: List<Team> = intent.getParcelableArrayListExtra("data")
        val posisi = intent.getIntExtra("posisi", 0)

        dataTeamId = dataTeam[posisi].idTeam.toString()

        supportActionBar?.title = dataTeam[posisi].strTeam

        val linkBadge = dataTeam[posisi].strTeamFanart2
        Picasso.get().load(linkBadge).into(detail_team_image)

        detail_team_stadium.text = dataTeam[posisi].strStadium
        detail_team_year.text = dataTeam[posisi].intFormedYear

        var detailOverview = dataTeam[posisi].strDescriptionEN
        var idTeam = dataTeam[posisi].idTeam

        settingTab(detailOverview, idTeam);

        /*favorit = FavoriteTeam(1,
                dataTeam[posisi].idTeam,
                dataTeam[posisi].strTeam,
                dataTeam[posisi].strStadium,
                dataTeam[posisi].intFormedYear,
                dataTeam[posisi].strDescriptionEN,
                dataTeam[posisi].strTeamBadge,
                dataTeam[posisi].strTeamFanart1,
                dataTeam[posisi].strTeamFanart2,
                dataTeam[posisi].strTeamFanart3,
                dataTeam[posisi].strTeamFanart4
        )*/

        favoriteState()
        setFavorite()
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

//    private fun addToFavorite() {
//        try {
//            database.use {
//                insert(FavoriteTeam.TABLE_TEAM,
//                        FavoriteTeam.TEAM_ID to favorit.idTeam,
//                        FavoriteTeam.TEAM_NAME to favorit.strTeam,
//                        FavoriteTeam.STADIUM to favorit.strStadium,
//                        FavoriteTeam.TEAM_YEAR to favorit.intFormedYear,
//                        FavoriteTeam.DESCRIPTION to favorit.strDescriptionEN,
//                        FavoriteTeam.TEAM_LOGO to favorit.strTeamBadge,
//                        FavoriteTeam.FANART1 to favorit.strTeamFanart1,
//                        FavoriteTeam.FANART2 to favorit.strTeamFanart2,
//                        FavoriteTeam.FANART3 to favorit.strTeamFanart3,
//                        FavoriteTeam.FANART4 to favorit.strTeamFanart4
//                )
//            }
//            toast("Added to favorite").show()
//        } catch (e: SQLiteConstraintException) {
//            toast(e.localizedMessage).show()
//        }
//    }

    /*private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteTeam.TABLE_TEAM, "(TEAM_ID = {id})",
                        "id" to dataTeamId)
            }
            toast("Removed to favorite").show()
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage).show()
        }
    }*/

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_action_added)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    /*private fun favoriteState() {
        database.use {
            val result = select(FavoriteTeam.TABLE_TEAM)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to dataTeamId)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }*/

    private lateinit var pagerAdapter: PagerAdapter

    /*private fun settingTab(detailOverview: String?, idTeam: String?) {
        pagerAdapter = PagerAdapter(supportFragmentManager, detailOverview, idTeam)
        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // Switch to view for this tab
                viewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }*/

    /*class PagerAdapter(fm: FragmentManager?, detailOverview: String?, idTeam: String?) : FragmentStatePagerAdapter(fm) {
        private lateinit var fragmentOverview :OverviewFragment
        private lateinit var fragmentPlayer :PlayerFragment
        var detail = detailOverview
        var idteam = idTeam

        override fun getItem(position: Int): Fragment? {
            val args1 = Bundle()
            args1.putString("parameter", detail)
            fragmentOverview = OverviewFragment()
            fragmentOverview.setArguments(args1)

            val args2 = Bundle()
            args2.putString("parameter", idteam)
            fragmentPlayer = PlayerFragment()
            fragmentPlayer.setArguments(args2)

            when (position) {
                0 -> return fragmentOverview
                1 -> return fragmentPlayer

                else -> return null
            }
        }*/

        override fun getCount(): Int {
            return 2;

        }

        private val tabTitles = arrayOf("Description", "Player")

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitles[position]
        }

    }
}
