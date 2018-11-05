package com.deonico.footballwiki.events.detail

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.deonico.footballwiki.R
import com.deonico.footballwiki.R.drawable.ic_add_to_favorite
import com.deonico.footballwiki.R.drawable.ic_added_to_favorite
import com.deonico.footballwiki.api.ApiRepository
import com.deonico.footballwiki.db.EventDB
import com.deonico.footballwiki.db.database
import com.deonico.footballwiki.model.Event
import com.deonico.footballwiki.model.Team
import com.deonico.footballwiki.util.changeFormatDate
import com.deonico.footballwiki.util.strTodate
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh

class EventsDetailActivity: AppCompatActivity(), EventsDetailView{
    private lateinit var event: Event
    private lateinit var presenter: EventsDetailPresenter
    private val table = EventDB

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        event = intent.getParcelableExtra("EVENT")

        val date = strTodate(event.dateEvent)
        tv_date.text = changeFormatDate(date)

        home_club.text = event.strHomeTeam
        home_score.text = event.intHomeScore

        away_club.text = event.strAwayTeam
        away_score.text = event.intAwayScore

        favoriteState()

        val request = ApiRepository()
        val gson = Gson()
        presenter = EventsDetailPresenter(this, request, gson)
        presenter.getEventDetail(event.idEvent,
            event.idHomeTeam,
            event.idAwayTeam)

        swipe_match.onRefresh {
            presenter.getEventDetail(event.idEvent,
                event.idHomeTeam,
                event.idAwayTeam)
        }
        swipe_match.setColorSchemeResources(
            R.color.colorAccent,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)
    }

    override fun showLoading() {
        swipe_match.isRefreshing = true
    }

    override fun hideLoading() {
        swipe_match.isRefreshing = false
    }

    override fun showDetail(matchDetails: List<Event>,
                            homeTeams: List<Team>,
                            awayTeams: List<Team>) {
        val eventDetail = matchDetails.get(0)
        val homeTeam = homeTeams.get(0)
        val awayTeam = awayTeams.get(0)

        Picasso.get().load(homeTeam.strTeamBadge).into(home_img)
        home_club.text = eventDetail.strHomeTeam
        home_score.text = eventDetail.intHomeScore
        home_formation.text = eventDetail.strHomeFormation
        home_goals.text = if(eventDetail.strHomeGoalDetails.isNullOrEmpty()) "-" else eventDetail.strHomeGoalDetails?.replace(";", ";\n")
        home_shots.text = eventDetail.intHomeShots ?: "-"
        home_goalkeeper.text = if(eventDetail.strHomeLineupGoalkeeper.isNullOrEmpty()) "-" else eventDetail.strHomeLineupGoalkeeper?.replace("; ", ";\n")
        home_defense.text = if(eventDetail.strHomeLineupDefense.isNullOrEmpty()) "-" else eventDetail.strHomeLineupDefense?.replace("; ", ";\n")
        home_midfield.text = if(eventDetail.strHomeLineupMidfield.isNullOrEmpty()) "-" else eventDetail.strHomeLineupMidfield?.replace("; ", ";\n")
        home_forward.text = if(eventDetail.strHomeLineupForward.isNullOrEmpty()) "-" else eventDetail.strHomeLineupForward?.replace("; ", ";\n")
        home_subtitutes.text = if(eventDetail.strHomeLineupSubstitutes.isNullOrEmpty()) "-" else eventDetail.strHomeLineupSubstitutes?.replace("; ", ";\n")

        Picasso.get().load(awayTeam.strTeamBadge).into(away_img)
        away_club.text = eventDetail.strAwayTeam
        away_score.text = eventDetail.intAwayScore
        away_formation.text = eventDetail.strAwayFormation
        away_goals.text = if(eventDetail.strAwayGoalDetails.isNullOrEmpty()) "-" else eventDetail.strAwayGoalDetails?.replace(";", ";\n")
        away_shots.text = eventDetail.intAwayShots ?: "-"
        away_goalkeeper.text = if(eventDetail.strAwayLineupGoalkeeper.isNullOrEmpty()) "-" else eventDetail.strAwayLineupGoalkeeper?.replace("; ", ";\n")
        away_defense.text = if(eventDetail.strAwayLineupDefense.isNullOrEmpty()) "-" else eventDetail.strAwayLineupDefense?.replace("; ", ";\n")
        away_midfield.text = if(eventDetail.strAwayLineupMidfield.isNullOrEmpty()) "-" else eventDetail.strAwayLineupMidfield?.replace("; ", ";\n")
        away_forward.text = if(eventDetail.strAwayLineupForward.isNullOrEmpty()) "-" else eventDetail.strAwayLineupForward?.replace("; ", ";\n")
        away_subtitutes.text = if(eventDetail.strAwayLineupSubstitutes.isNullOrEmpty()) "-" else eventDetail.strAwayLineupSubstitutes?.replace("; ", ";\n")

        hideLoading()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
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
                insert(table.TABLE_EVENT,
                    table.EVENT_ID to event.idEvent,
                    table.EVENT_NAME to event.strEvent,
                    table.EVENT_DATE to event.dateEvent,
                    table.ID_HOME to event.idHomeTeam,
                    table.HOME_TEAM to event.strHomeTeam,
                    table.HOME_SCORE to event.intHomeScore,
                    table.ID_AWAY to event.idAwayTeam,
                    table.AWAY_TEAM to event.strAwayTeam,
                    table.AWAY_SCORE to event.intAwayScore)
            }
            Snackbar.make(swipe_match,"Added to favorite", Snackbar.LENGTH_LONG).show()
        }catch (e: SQLiteConstraintException){
            Snackbar.make(swipe_match,e.localizedMessage, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use{
                delete(table.TABLE_EVENT, "(EVENT_ID = {id})", "id" to event.idEvent.orEmpty())
            }
            Snackbar.make(swipe_match,"Removed to favorite", Snackbar.LENGTH_LONG).show()
        } catch (e: SQLiteConstraintException){
            Snackbar.make(swipe_match,e.localizedMessage, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setFavorite(){
        val icon = if(isFavorite) ic_added_to_favorite else ic_add_to_favorite

        menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, icon)
    }

    private fun favoriteState(){
        database.use {
            val result = select(table.TABLE_EVENT)
                .whereArgs("(EVENT_ID = {id})", "id" to event.idEvent.orEmpty())
            val favorite = result.parseList(
                classParser<EventDB
                        >()
            )
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

}