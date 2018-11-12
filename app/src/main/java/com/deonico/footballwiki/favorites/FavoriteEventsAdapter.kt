package com.deonico.footballwiki.favorites

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.CalendarContract
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.deonico.footballwiki.R
import com.deonico.footballwiki.db.EventDB
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

class FavoriteEventsAdapter(private val events: List<EventDB>,
                            private val listener: (EventDB) -> Unit):
    RecyclerView.Adapter<FavoriteEventsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int):
            ViewHolder {
        return ViewHolder(
            LayoutInflater.
                from(parent.context).
                inflate(R.layout.item_list_event,
                    parent,
                    false))
    }

    override fun onBindViewHolder(holder: ViewHolder, 
                                  position: Int) {
        holder.bindItem(events[position], listener)
    }

    override fun getItemCount() = events.count()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val eventDate: TextView = itemView.find(R.id.tv_date)
        private val eventTime: TextView = itemView.find(R.id.tv_time)
        private val homeTeam: TextView = itemView.find(R.id.tv_home_name)
        private val homeScore: TextView = itemView.find(R.id.tv_home_score)
        private val awayTeam: TextView = itemView.find(R.id.tv_away_name)
        private val awayScore: TextView = itemView.find(R.id.tv_away_score)
        private val btnNotify: ImageView = itemView.find(R.id.iv_alarm)

        @SuppressLint("SimpleDateFormat")
        fun bindItem(event: EventDB, listener: (EventDB) -> Unit){
            val date = strTodate(event.dateEvent)
            val dateTime = toGMTFormat(event.dateEvent, event.strTime)
            val formatDateLocale = changeFormatDate(date)
            val formatTime = SimpleDateFormat("HH:mm").format(dateTime)
            val formatTimeAlarm = SimpleDateFormat("HH:mm:ss").format(dateTime)
            eventDate.text = formatDateLocale
            eventTime.text = formatTime

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

                val dateTime = formatDateLocale + " " + formatTimeAlarm
                val startTime = dateTime.dateTimeToFormat()
                val endTime = startTime + TimeUnit.MINUTES.toMillis(90)

                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime)
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
                intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE)
                intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                intent.putExtra(CalendarContract.Events.TITLE, event.strEvent)
                intent.putExtra(CalendarContract.Events.DESCRIPTION, event.strFilename)
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, event.strHomeTeam + " Stadium")
                intent.putExtra(CalendarContract.CalendarAlerts.ALARM_TIME, TimeUnit.MINUTES.toMillis(90))

                itemView.context.startActivity(intent)
            }
        }
    }

}