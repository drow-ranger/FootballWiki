package com.deonico.footballwiki.util

import android.annotation.SuppressLint
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone(){
    visibility = View.GONE
}


@SuppressLint("SimpleDateFormat")
fun  strTodate(strDate: String?, pattern: String= "yyyy-MM-dd"): Date {
    val format = SimpleDateFormat(pattern)
    val date = format.parse(strDate)

    return date
}

@SuppressLint("SimpleDateFormat")
fun changeFormatDate(date: Date?): String? = with(date ?: Date()){
    SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault())
        .format(this)
}

@SuppressLint("SimpleDateForamt")
fun toGMTFormat(date: String?, time:String?): Date? {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val dateTime = "$date $time"
    return formatter.parse(dateTime)
}

fun String.dateTimeToFormat(format: String = "E, dd MMM yyyy HH:mm:ss"): Long {

    val formatter = SimpleDateFormat(format)
    val date = formatter.parse(this)

    return date.time
}