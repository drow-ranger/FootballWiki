package com.deonico.footballwiki.events

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.deonico.footballwiki.R
import com.deonico.footballwiki.events.detail.EventsDetailActivity
import com.deonico.footballwiki.model.Event
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class EventsAdapter(private val events: List<Event>)
    : RecyclerView.Adapter<MatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_event, parent, false))
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItem(events[position])
        holder.itemView.onClick {
            holder.itemView.context.
                startActivity<EventsDetailActivity>("data" to events,
                    "posisi" to position )}
    }

    override fun getItemCount(): Int = events.size

}


class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val matchTime: TextView = view.find(R.id.match_time)
    private val matchHomeTeam: TextView = view.find(R.id.match_home_team)
    private val matchHomeScore: TextView = view.find(R.id.match_home_score)
    private val matchAwayScore: TextView = view.find(R.id.match_away_score)
    private val matchAwayTeam: TextView = view.find(R.id.match_away_team)

    fun bindItem(events: Event) {

        matchTime.text = events.dateEvent
        matchHomeTeam.text = events.strHomeTeam
        matchAwayTeam.text = events.strAwayTeam
        matchHomeScore.text = events.intHomeScore
        matchAwayScore.text = events.intAwayScore


    }
}