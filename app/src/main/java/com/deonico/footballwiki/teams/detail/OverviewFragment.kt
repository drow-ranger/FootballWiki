package com.deonico.footballwiki.teams.detail


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.deonico.footballwiki.R
import com.deonico.footballwiki.model.Team
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.nestedScrollView

class OverviewFragment: Fragment(), AnkoComponent<Context> {
    private lateinit var team: Team
    private lateinit var teamDescription: TextView

    companion object {
        fun newFragment(team: Team): OverviewFragment {
            val fragment = OverviewFragment()
            fragment.team = team

            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        teamDescription.text = team.strDescriptionEN
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>) = with(ui){
        linearLayout {
            lparams(width = matchParent, height = matchParent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            nestedScrollView {
                id = R.id.team_detail
                isVerticalScrollBarEnabled = false

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(10)
                    orientation = LinearLayout.VERTICAL
                    gravity = Gravity.CENTER_HORIZONTAL

                    teamDescription = textView().lparams(width = matchParent, height = wrapContent){
                        topMargin = dip(8)
                        bottomMargin = dip(24)
                    }
                }
            }
        }
    }
}