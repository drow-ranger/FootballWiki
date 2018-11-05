package com.deonico.footballwiki.events.previous

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.deonico.footballwiki.R
import com.deonico.footballwiki.model.Event
import com.deonico.footballwiki.util.changeFormatDate
import com.deonico.footballwiki.util.gone
import com.deonico.footballwiki.util.strTodate
import com.deonico.footballwiki.util.toGMTFormat
import kotlinx.android.synthetic.main.item_list_event.view.*
import org.jetbrains.anko.sdk15.coroutines.onClick
import java.text.SimpleDateFormat

class EventsPreviousAdapter(private val events: List<Event>,
                            private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<EventsPreviousAdapter.EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        return EventsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_event,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

    override fun getItemCount(): Int = events.size


    class EventsViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bindItem(event: Event, listener: (Event) -> Unit) {
            with(itemView) {
                val date = strTodate(event.dateEvent)
                val dateTime = toGMTFormat(event.dateEvent, event.strTime)
                tv_date.text = changeFormatDate(date)
                tv_time.text = SimpleDateFormat("hh:mm a").format(dateTime)
                tv_home_name.text = event.strHomeTeam
                tv_home_score.text = event.intHomeScore
                tv_away_name.text = event.strAwayTeam
                tv_away_score.text = event.intAwayScore
                iv_alarm.gone()

                onClick {
                    listener(event)
                }

            }

        }
    }

}

