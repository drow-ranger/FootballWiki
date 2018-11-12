package com.deonico.footballwiki.teams

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.deonico.footballwiki.R
import com.deonico.footballwiki.model.Team
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class TeamsAdapter(private val teams: List<Team>,
                   private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_team, parent, false))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount(): Int = teams.size

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val teamName: TextView = itemView.find(R.id.tv_name)
        private val teamBadge: ImageView = itemView.find(R.id.iv_icon)
        private val teamDescription: TextView = itemView.find(R.id.tv_description)

        fun bindItem(teams: Team, listener: (Team) -> Unit) {
            Picasso.get().load(teams.strTeamBadge).into(teamBadge)
            teamName.text = teams.strTeam
            teamDescription.text = teams.strDescriptionEN

            itemView.onClick {
                listener(teams)
            }

        }
    }

}

