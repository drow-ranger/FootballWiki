package com.deonico.footballwiki.events.detail

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.deonico.footballwiki.db.EventDB
import com.deonico.footballwiki.model.Event
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.deonico.footballwiki.R
import com.deonico.footballwiki.R.drawable.ic_add_to_favorite
import com.deonico.footballwiki.R.drawable.ic_added_to_favorite
import com.deonico.footballwiki.api.ApiRepository
import com.deonico.footballwiki.api.TheSportDBApi
import com.deonico.footballwiki.db.database
import com.deonico.footballwiki.model.TeamResponse
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class EventsDetailActivity : AppCompatActivity() {

    private lateinit var favorite: EventDB

    private lateinit var dataEventId: String

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        val dataMatch: List<Event> = intent.getParcelableArrayListExtra("data")
        val posisi = intent.getIntExtra("posisi", 0)

        val idHomeTeam = dataMatch[posisi].idHomeTeam
        val idAwayTeam = dataMatch[posisi].idAwayTeam


        val imgHome = detail_home_logo
        val imgAway = detail_away_logo

        getBadge(idHomeTeam, imgHome)
        getBadge(idAwayTeam, imgAway)

        dataEventId = dataMatch[posisi].idEvent.toString()
        val dataEventDate = dataMatch[posisi].dateEvent
        val dataHomeTeam = dataMatch[posisi].strHomeTeam
        val dataAwayTeam = dataMatch[posisi].strAwayTeam
        val dataHomeScore = dataMatch[posisi].intHomeScore
        val dataAwayScore = dataMatch[posisi].intAwayScore
        val dataHomeShots = dataMatch[posisi].intHomeShots
        val dataAwayShots = dataMatch[posisi].intAwayShots
        val dataHomeGoal = dataMatch[posisi].strHomeGoalDetails?.replace(";".toRegex(), "\n")
        val dataAwayGoal = dataMatch[posisi].strAwayGoalDetails?.replace(";".toRegex(), "\n")
        val dataHomeYellow = dataMatch[posisi].strHomeYellowCards?.replace(";".toRegex(), "\n")
        val dataAwayYellow = dataMatch[posisi].strAwayYellowCards?.replace(";".toRegex(), "\n")
        val dataHomeRed = dataMatch[posisi].strHomeRedCards?.replace(";".toRegex(), "\n")
        val dataAwayRed = dataMatch[posisi].strAwayRedCards?.replace(";".toRegex(), "\n")
        val dataIdHome = dataMatch[posisi].idHomeTeam
        val dataIdAway = dataMatch[posisi].idAwayTeam

        favorite = EventDB(1, dataEventId,
            dataEventDate,
            dataHomeTeam,
            dataAwayTeam,
            dataHomeScore,
            dataAwayScore,
            dataHomeShots,
            dataAwayShots,
            dataHomeGoal,
            dataAwayGoal,
            dataHomeYellow,
            dataAwayGoal,
            dataHomeRed,
            dataAwayRed,
            dataIdHome,
            dataIdAway
        )

        detail_match_time.text = dataEventDate

        detail_home_team.text = dataHomeTeam
        detail_away_team.text = dataAwayTeam
        detail_home_score.text = dataHomeScore
        detail_away_score.text = dataAwayScore

        detail_home_shots.text = dataHomeShots
        detail_away_shots.text = dataAwayShots

        detail_home_goal.text = dataHomeGoal?.replace(";".toRegex(), "\n")
        detail_away_goal.text = dataAwayGoal?.replace(";".toRegex(), "\n")
        detail_home_yellow.text = dataHomeYellow?.replace(";", "\n")
        detail_away_yellow.text = dataAwayYellow?.replace(";".toRegex(), "\n")
        detail_home_red.text = dataHomeRed?.replace(";".toRegex(), "\n")
        detail_away_red.text = dataAwayRed?.replace(";".toRegex(), "\n")


        favoriteState()
        setFavorite()

    }

    private fun getBadge(idTeam: String?, imageView: ImageView) {

        val api = ApiRepository()
        val gson = Gson()

        var data: TeamResponse
        doAsync {

            data = gson.fromJson(api
                .doRequest(TheSportDBApi.getTeamDetail(idTeam)),
                TeamResponse::class.java
            )

            uiThread {
                val linkBadge = data.teams.get(0).strTeamBadge
                Picasso.get().load(linkBadge).into(imageView)
            }
        }
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

    private fun addToFavorite() {
        try {
            database.use {
                insert(EventDB.TABLE_EVENT,
                    EventDB.EVENT_ID to favorite.idEvent,
                    EventDB.EVENT_ID to favorite.idEvent,
                    EventDB.EVENT_DATE to favorite.dateEvent,
                    EventDB.HOME_TEAM to favorite.strHomeTeam,
                    EventDB.AWAY_TEAM to favorite.strAwayTeam,
                    EventDB.HOME_SCORE to favorite.intHomeScore,
                    EventDB.AWAY_SCORE to favorite.intAwayScore,
                    EventDB.HOME_SHOTS to favorite.intHomeShots,
                    EventDB.AWAY_SHOTS to favorite.intAwayShots,
                    EventDB.HOME_GOAL to favorite.strHomeGoalDetails,
                    EventDB.AWAY_GOAL to favorite.strAwayGoalDetails,
                    EventDB.HOME_YELLOW to favorite.strHomeYellowCards,
                    EventDB.AWAY_YELLOW to favorite.strAwayYellowCards,
                    EventDB.HOME_RED to favorite.strHomeRedCards,
                    EventDB.AWAY_RED to favorite.strAwayRedCards,
                    EventDB.ID_HOME to favorite.idHomeTeam,
                    EventDB.ID_AWAY to favorite.idAwayTeam

                )
            }
            toast("Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(EventDB.TABLE_EVENT, "(EVENT_ID = {id})",
                    "id" to dataEventId)
            }
            toast("Removed to favorite").show()
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorite)
    }

    private fun favoriteState() {
        database.use {
            val result = select(EventDB.TABLE_EVENT)
                .whereArgs("(EVENT_ID = {id})",
                    "id" to dataEventId)
            val favorite = result.parseList(classParser<EventDB>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}
