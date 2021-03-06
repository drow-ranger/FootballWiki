package com.deonico.footballwiki.players.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.deonico.footballwiki.R
import com.deonico.footballwiki.model.Player
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class PlayersAdapter(private val players: List<Player>,
                     private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayersAdapter.PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_player, parent, false))
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }

    override fun getItemCount(): Int = players.size

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val playerName: TextView = itemView.find(R.id.tv_name)
        private val playerPosition: TextView = itemView.find(R.id.tv_position)
        private val playerDescription: TextView = itemView.find(R.id.tv_description)
        private val playerThumb: ImageView = itemView.find(R.id.iv_icon)

        fun bindItem(players: Player, listener: (Player) -> Unit) {
            Picasso.get().load(players.strThumb).into(playerThumb)
            playerName.text = players.strPlayer
            playerPosition.text = players.strPosition
            playerDescription.text = players.strDescriptionEN

            itemView.onClick {
                listener(players)
            }
        }
    }

}