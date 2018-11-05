package com.deonico.footballwiki.events

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.deonico.footballwiki.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class EventUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui){
        cardView {
            lparams(width = matchParent, height = wrapContent){
                gravity = Gravity.CENTER
                margin = dip(4)
                radius = 4f
            }

            linearLayout {
                orientation = LinearLayout.VERTICAL

                relativeLayout {
                    textView("Minggu, 04 Maret 2018"){
                        id = R.id.date
                        textColorResource = R.color.colorPrimary
                        topPadding = dip(8)
                        bottomPadding = dip(4)

                    }.lparams(width = wrapContent, height = wrapContent){
                        centerInParent()
                    }

                    imageButton(R.drawable.ic_alarm){
                        id = R.id.btn_notify
                        backgroundColor = Color.WHITE
                        topPadding = dip(8)
                        rightPadding = dip(8)
                        visibility = View.INVISIBLE
                    }.lparams(width = wrapContent, height = wrapContent){
                        alignParentRight()
                    }

                }.lparams(width = matchParent, height = wrapContent)

                textView("21:00"){
                    id = R.id.time
                    textSize = 10f
                    textColorResource = R.color.colorPrimary
                    bottomPadding = dip(4)

                }.lparams(width = wrapContent, height = wrapContent){
                    gravity = Gravity.CENTER
                }

                relativeLayout {

                    textView{
                        id = R.id.homeTeam
                        textSize = 14f
                        textColor = Color.BLACK
                        gravity = Gravity.END
                    }.lparams(width = wrapContent, height = wrapContent){
                        leftOf(R.id.homeScore)
                        rightMargin = dip(10)
                    }

                    textView{
                        id = R.id.homeScore
                        textSize = 12f
                        gravity = Gravity.CENTER
                        textColor = Color.BLACK
                    }.lparams(width = wrapContent, height = wrapContent){
                        leftOf(R.id.vs)
                    }

                    textView("vs"){
                        id = R.id.vs
                        textSize = 10f
                        gravity = Gravity.CENTER
                    }.lparams(width = wrapContent, height = wrapContent){
                        centerInParent()
                        leftMargin = dip(6)
                        rightMargin = dip(6)
                    }

                    textView{
                        id = R.id.awayScore
                        textSize = 12f
                        gravity = Gravity.CENTER
                        textColor = Color.BLACK
                    }.lparams(width = wrapContent, height = wrapContent){
                        rightOf(R.id.vs)
                    }

                    textView{
                        id = R.id.awayTeam
                        textSize = 14f
                        textColor = Color.BLACK
                        gravity = Gravity.START
                    }.lparams(width = wrapContent, height = wrapContent){
                        rightOf(R.id.awayScore)
                        leftMargin = dip(10)
                    }

                }.lparams(width = matchParent, height = wrapContent)

            }.lparams(width = matchParent, height = wrapContent){
                gravity = Gravity.CENTER
                bottomMargin = dip(8)
            }
        }
    }

}