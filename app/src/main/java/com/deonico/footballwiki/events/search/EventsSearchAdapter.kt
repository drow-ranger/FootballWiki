package com.deonico.footballwiki.events.search

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.deonico.footballwiki.R
import com.deonico.footballwiki.events.EventUI
import com.deonico.footballwiki.model.Event
import com.deonico.footballwiki.util.changeFormatDate
import com.deonico.footballwiki.util.strTodate
import com.deonico.footballwiki.util.toGMTFormat
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat

class EventsSearchAdapter(private val events: List<Event>,
                          private val listener: (Event) -> Unit):
    RecyclerView.Adapter<EventsSearchAdapter.EventViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int) =
        EventViewHolder(
            EventUI().
            createView(AnkoContext.
                create(parent.context, parent)))

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) = holder.bindItem(events[position], listener)

    class EventViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val eventDate: TextView = itemView.find(R.id.date)
        private val eventTime: TextView = itemView.find(R.id.time)
        private val homeTeam: TextView = itemView.find(R.id.homeTeam)
        private val homeScore: TextView = itemView.find(R.id.homeScore)
        private val awayTeam: TextView = itemView.find(R.id.awayTeam)
        private val awayScore: TextView = itemView.find(R.id.awayScore)

        @SuppressLint("SimpleDateFormat")
        fun bindItem(event: Event, listener: (Event) -> Unit){
            val date = strTodate(event.dateEvent)
            val dateTime = toGMTFormat(event.dateEvent, event.strTime)
            eventDate.text = changeFormatDate(date)
            eventTime.text = SimpleDateFormat("HH:mm").format(dateTime)

            homeTeam.text = event.strHomeTeam
            homeScore.text = event.intHomeScore

            awayTeam.text = event.strAwayTeam
            awayScore.text = event.intAwayScore

            itemView.onClick { listener(event) }
        }
    }
}