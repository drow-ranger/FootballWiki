package com.deonico.footballwiki.favorites

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.deonico.footballwiki.R
import com.deonico.footballwiki.db.TeamDB
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoriteTeamsAdapter(private val teams: List<TeamDB>,
                           private val listener: (TeamDB) -> Unit)
    : RecyclerView.Adapter<FavoriteTeamsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int):
            ViewHolder {
        return ViewHolder(LayoutInflater.
            from(parent.context).
            inflate(R.layout.item_list_team,
                parent,
                false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount() = teams.count()


    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val teamName: TextView = itemView.find(R.id.tv_name)
        private val teamBadge: ImageView = itemView.find(R.id.iv_icon)
        private val teamDescription: TextView = itemView.find(R.id.tv_description)

        fun bindItem(teams: TeamDB, listener: (TeamDB) -> Unit){
            Picasso.get().load(teams.strTeamBadge).into(teamBadge)
            teamName.text = teams.strTeam
            teamDescription.text = teams.strDescriptionEN

            itemView.onClick {
                listener(teams)
            }

        }
    }

}

