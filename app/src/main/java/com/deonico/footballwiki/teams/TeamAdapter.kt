package com.deonico.footballwiki.teams

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.deonico.footballwiki.R
import com.deonico.footballwiki.model.Team
import com.deonico.footballwiki.teams.detail.TeamDetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_team.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class TeamAdapter(private val teams: List<Team>,
                  private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_team, parent, false))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
        //holder.itemView.onClick {holder.itemView.context.startActivity<TeamDetailActivity>("data" to teams, "posisi" to position )}
    }

    override fun getItemCount(): Int = teams.size

}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){

    fun bindItem(teams: Team, listener: (Team) -> Unit) {
        Picasso.get().load(teams.strTeamBadge).into(itemView.iv_icon)
        itemView.tv_name.text = teams.strTeam
        itemView.tv_description.text = teams.strDescriptionEN

        itemView.onClick {
            listener(teams)
        }

    }
}