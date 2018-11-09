package com.deonico.footballwiki.events

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.CalendarContract
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.deonico.footballwiki.R
import com.deonico.footballwiki.model.Event
import com.deonico.footballwiki.util.changeFormatDate
import com.deonico.footballwiki.util.dateTimeToFormat
import com.deonico.footballwiki.util.strTodate
import com.deonico.footballwiki.util.toGMTFormat
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class EventsListAdapter(private val events: List<Event>,
                        private val listener: (Event) -> Unit):
    RecyclerView.Adapter<EventsListAdapter.EventViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int) =
        EventViewHolder(EventUI().
            createView(AnkoContext.
                create(parent.context, parent)))

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventViewHolder,
                                  position: Int) =
        holder.bindItem(events[position], listener)

    class EventViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val eventDate: TextView = itemView.find(R.id.date)
        private val eventTime: TextView = itemView.find(R.id.time)
        private val homeTeam: TextView = itemView.find(R.id.homeTeam)
        private val homeScore: TextView = itemView.find(R.id.homeScore)
        private val awayTeam: TextView = itemView.find(R.id.awayTeam)
        private val awayScore: TextView = itemView.find(R.id.awayScore)
        private val btnNotify: ImageButton = itemView.find(R.id.btn_notify)

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

            if(dateTime!!.after(Date())){
                btnNotify.visibility = View.VISIBLE
            }

            itemView.onClick { listener(event) }
            btnNotify.onClick {
                val intent = Intent(Intent.ACTION_INSERT)
                intent.type = "vnd.android.cursor.item/event"

                val dateTime = event.dateEvent + " " + event.strTime
                Log.d("dateTime", "DateTime adalah " + dateTime)
                val startTime = dateTime.dateTimeToFormat()
                Log.d("startTime", "startTime adalah " + startTime)
                val endTime = startTime + TimeUnit.MINUTES.toMillis(90)
                Log.d("endTime", "endTime adalah " + endTime)

                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
                intent.putExtra(CalendarContract.CalendarAlerts.ALARM_TIME, TimeUnit.MINUTES.toMillis(90))

                intent.putExtra(
                    CalendarContract.Events.TITLE,
                    "${event.strHomeTeam} vs ${event.strAwayTeam}")

                intent.putExtra(
                    CalendarContract.Events.DESCRIPTION,
                    "Reminder ${event.strHomeTeam} and ${event.strAwayTeam} from My Football App")


                itemView.context.startActivity(intent)
            }
        }
    }
}