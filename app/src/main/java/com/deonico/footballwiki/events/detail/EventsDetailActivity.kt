package com.deonico.footballwiki.events.detail

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.jetbrains.anko.db.classParser
import com.deonico.footballwiki.R
import com.deonico.footballwiki.R.color.colorAccent
import com.deonico.footballwiki.R.drawable.ic_added_to_favorite
import com.deonico.footballwiki.R.drawable.ic_add_to_favorite
import com.deonico.footballwiki.api.ApiRepository
import com.deonico.footballwiki.db.EventDB
import com.deonico.footballwiki.db.database
import com.deonico.footballwiki.model.Event
import com.deonico.footballwiki.model.Team
import com.deonico.footballwiki.util.changeFormatDate
import com.deonico.footballwiki.util.strTodate
import com.deonico.footballwiki.util.toGMTFormat
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh
import java.text.SimpleDateFormat

class EventsDetailActivity : AppCompatActivity(), EventsDetailView {
    private lateinit var event: Event
    private lateinit var presenter: EventsDetailPresenter

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        event = intent.getParcelableExtra("eventData")

        supportActionBar?.title = event.strEvent

        val date = strTodate(event.dateEvent)
        val dateTime = toGMTFormat(event.dateEvent, event.strTime)
        val formatDateLocale = changeFormatDate(date)
        val formatTime = SimpleDateFormat("HH:mm").format(dateTime)

        tv_date.text = formatDateLocale

        tv_team_home.text = event.strHomeTeam
        tv_home_score.text = event.intHomeScore

        tv_team_away.text = event.strAwayTeam
        tv_away_score.text = event.intAwayScore

        favoriteState()

        val request = ApiRepository()
        val gson = Gson()
        presenter = EventsDetailPresenter(this, request, gson)
        presenter.getEventDetail(event.idEvent, event.idHomeTeam, event.idAwayTeam)

        swipeRefresh.onRefresh {
            presenter.getEventDetail(event.idEvent, event.idHomeTeam, event.idAwayTeam)
        }
        swipeRefresh.setColorSchemeResources(colorAccent,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)
    }

    private fun parserGoal(input: String): String {
        return input.replace(";", "\n", false)
    }

    private fun parser(input: String): String {
        return input.replace("; ", "\n", false)
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun showDetail(matchDetails: List<Event>, homeTeams: List<Team>, awayTeams: List<Team>) {
        val eventDetail = matchDetails.get(0)
        val homeTeam = homeTeams.get(0)
        val awayTeam = awayTeams.get(0)

        Picasso.get().load(homeTeam.strTeamBadge).into(iv_logo_home)
        tv_team_home.text = event.strHomeTeam ?: " "
        tv_home_score.text = event.intHomeScore ?: " "
        tv_home_formation.text = event.strHomeFormation ?: " "
        tv_home_goal.text = parserGoal(event.strHomeGoalDetails ?: " ")
        tv_home_shot.text = event.intHomeShots ?: " "
        tv_home_goalkeeper.text = parserGoal(event.strHomeLineupGoalkeeper ?: " ")
        tv_home_defense.text = parser(event.strHomeLineupDefense ?: " ")
        tv_home_midlefield.text = parser(event.strHomeLineupMidfield ?: " ")
        tv_home_forward.text = parser(event.strHomeLineupForward ?: " ")
        tv_home_subtituties.text = parser(event.strHomeLineupSubstitutes ?: " ")
        tv_home_yellowcard.text = parser(event.strHomeYellowCards ?: " ")
        tv_home_redcard.text = parser(event.strHomeRedCards ?: " ")

        Picasso.get().load(awayTeam.strTeamBadge).into(iv_logo_away)
        tv_team_away.text = event.strAwayTeam ?: " "
        tv_away_score.text = event.intAwayScore ?: " "
        tv_away_formation.text = event.strAwayFormation ?: " "
        tv_away_goal.text = parserGoal(event.strAwayGoalDetails ?: " ")
        tv_away_shot.text = event.intAwayShots ?: " "
        tv_away_goalkeeper.text = parserGoal(event.strAwayLineupGoalkeeper ?: " ")
        tv_away_defense.text = parser(event.strAwayLineupDefense ?: " ")
        tv_away_midlefield.text = parser(event.strAwayLineupMidfield ?: " ")
        tv_away_forward.text = parser(event.strAwayLineupForward ?: " ")
        tv_away_subtituties.text = parser(event.strAwayLineupSubstitutes ?: " ")
        tv_away_yellowcard.text = parser(event.strAwayYellowCards ?: " ")
        tv_away_redcard.text = parser(event.strAwayRedCards ?: " ")

        hideLoading()
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
        try {
            database.use {
                insert(EventDB.TABLE_EVENT,
                    EventDB.EVENT_ID to event.idEvent,
                    EventDB.EVENT_NAME to event.strEvent,
                    EventDB.EVENT_FILENAME to event.strFilename,
                    EventDB.EVENT_DATE to event.dateEvent,
                    EventDB.EVENT_TIME to event.strTime,
                    EventDB.HOME_TEAM_ID to event.idHomeTeam,
                    EventDB.HOME_TEAM_NAME to event.strHomeTeam,
                    EventDB.HOME_TEAM_SCORE to event.intHomeScore,
                    EventDB.AWAY_TEAM_ID to event.idAwayTeam,
                    EventDB.AWAY_TEAM_NAME to event.strAwayTeam,
                    EventDB.AWAY_TEAM_SCORE to event.intAwayScore)
            }
            Snackbar.make(event_detail_viewpager,R.string.add_favorite, Snackbar.LENGTH_LONG).show()
        }catch (e: SQLiteConstraintException){
            Snackbar.make(event_detail_viewpager,e.localizedMessage, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use{
                delete(EventDB.TABLE_EVENT, "(EVENT_ID = {id})", "id" to event.idEvent.orEmpty())
            }
            Snackbar.make(event_detail_viewpager,R.string.remove_favorite, Snackbar.LENGTH_LONG).show()
        }catch (e: SQLiteConstraintException){
            Snackbar.make(event_detail_viewpager,e.localizedMessage, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setFavorite(){
        val icon = if(isFavorite) ic_added_to_favorite else ic_add_to_favorite

        menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, icon)
    }

    private fun favoriteState(){
        database.use {
            val result = select(EventDB.TABLE_EVENT)
                .whereArgs("(EVENT_ID = {id})", "id" to event.idEvent.orEmpty())
            val favorite = result.parseList(classParser<EventDB
                    >())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

}