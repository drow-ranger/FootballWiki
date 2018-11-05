package com.deonico.footballwiki.events.detail

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.R.attr.colorAccent
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.deonico.footballwiki.R
import com.deonico.footballwiki.api.ApiRepository
import com.deonico.footballwiki.db.EventDB
import com.deonico.footballwiki.db.database
import com.deonico.footballwiki.model.Event
import com.deonico.footballwiki.model.Team
import com.deonico.footballwiki.util.changeFormatDate
import com.deonico.footballwiki.util.dateTimeToFormat
import com.deonico.footballwiki.util.strTodate
import com.deonico.footballwiki.util.toGMTFormat
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import java.text.SimpleDateFormat

class EventDetailActivity: AppCompatActivity(), EventDetailView{
    private lateinit var event: Event
    private lateinit var presenter: EventDetailPresenter
    private val table = EventDB

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private fun parserGoal(input: String): String {
        return input.replace(";", "\n", false)
    }

    private fun parser(input: String): String {
        return input.replace("; ", "\n", false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        event = intent.getParcelableExtra("eventObject")

        val date = strTodate(event.dateEvent)
        tv_date.text = changeFormatDate(date)

        val dateTime = toGMTFormat(event.dateEvent, event.strTime)
        tv_time.text = SimpleDateFormat("hh:mm a").format(dateTime)

        team_home.text = event.strHomeTeam
        home_score.text = event.intHomeScore

        team_away.text = event.strAwayTeam
        away_score.text = event.intAwayScore

        //favoriteState()

        val request = ApiRepository()
        val gson = Gson()
        presenter = EventDetailPresenter(this, request, gson)
        presenter.getEventDetail(event.idEvent, event.idHomeTeam, event.idAwayTeam)
    }

    override fun showDetail(eventDetail: List<Event>, homeTeams: List<Team>, awayTeams: List<Team>) {
        val eventDetail = eventDetail.get(0)
        val homeTeam = homeTeams.get(0)
        val awayTeam = awayTeams.get(0)

        Picasso.get().load(homeTeam.strTeamBadge).into(logo_home)
        team_home.text = eventDetail.strHomeTeam
        home_score.text = eventDetail.intHomeScore
        home_goal.text = parserGoal(event.strHomeGoalDetails ?: "-")
        home_goalkeeper.text = parser(event.strHomeLineupGoalkeeper?: "-")
        home_defense.text = parser(event.strAwayLineupDefense?: "-")
        home_midlefield.text = parser(event.strHomeLineupMidfield?: "-")
        home_forward.text = parser(event.strHomeLineupForward?: "-")
        home_subtituties.text = parser(event.strHomeLineupSubstitutes?: "-")

        Picasso.get().load(awayTeam.strTeamBadge).into(logo_away)
        team_away.text = eventDetail.strAwayTeam
        away_score.text = eventDetail.intAwayScore
        away_goal.text = parserGoal(event.strAwayGoalDetails?: "-")
        away_goalkeeper.text = parser(event.strAwayLineupGoalkeeper?: "-")
        away_defense.text = parser(event.strAwayLineupDefense?: "-")
        away_midlefield.text = parser(event.strAwayLineupMidfield?: "-")
        away_forward.text = parser(event.strAwayLineupForward?: "-")
        away_subtituties.text = parser(event.strAwayLineupSubstitutes?: "-")
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
                    table.EVENT_DATE to event.dateEvent,
                    table.EVENT_TIME to event.strTime,
                    table.HOME_TEAM to event.strHomeTeam,
                    table.AWAY_TEAM to event.strAwayTeam,
                    table.HOME_SCORE to event.intHomeScore,
                    table.AWAY_SCORE to event.intAwayScore,
                    table.HOME_GOAL to event.strHomeGoalDetails,
                    table.AWAY_GOAL to event.strAwayGoalDetails,
                    table.HOME_GK to event.strHomeLineupGoalkeeper,
                    table.AWAY_GK to event.strAwayLineupGoalkeeper,
                    table.HOME_DEF to event.strHomeLineupDefense,
                    table.AWAY_DEF to event.strAwayLineupDefense,
                    table.HOME_MDF to event.strHomeLineupMidfield,
                    table.AWAY_MDF to event.strAwayLineupMidfield,
                    table.HOME_FW to event.strHomeLineupForward,
                    table.AWAY_FW to event.strAwayLineupForward,
                    table.HOME_SUB to event.strHomeLineupSubstitutes,
                    table.AWAY_SUB to event.strAwayLineupSubstitutes,
                    table.ID_HOME to event.idHomeTeam,
                    table.ID_AWAY to event.idAwayTeam)
            }
            Snackbar.make(event_detail_viewpager,"Added to favorite", Snackbar.LENGTH_LONG).show()
        }catch (e: SQLiteConstraintException){
            Snackbar.make(event_detail_viewpager,e.localizedMessage, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use{
                delete(table.TABLE_EVENT, "(EVENT_ID = {id})", "id" to event.idEvent.orEmpty())
            }
            Snackbar.make(event_detail_viewpager,"Removed to favorite", Snackbar.LENGTH_LONG).show()
        } catch (e: SQLiteConstraintException){
            Snackbar.make(event_detail_viewpager,e.localizedMessage, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setFavorite(){
        val icon =
            if(isFavorite) R.drawable.ic_added_to_favorite
            else R.drawable.ic_add_to_favorite

        menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, icon)
    }

    private fun favoriteState(){
        database.use {
            val result = select(table.TABLE_EVENT)
                    .whereArgs("(EVENT_ID = {id})", "id" to event.idEvent.orEmpty())
            val favorite = result.parseList(classParser<EventDB>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

}