package com.deonico.footballwiki.players.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.deonico.footballwiki.R
import com.deonico.footballwiki.model.Player
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_player.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class PlayerAdapter(private val players: List<Player>,
                    private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        //return TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
        return TeamViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_player, parent, false))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
        //holder.itemView.onClick {holder.itemView.context.startActivity<PlayerDetailActivity>("data" to players, "posisi" to position )}
    }

    override fun getItemCount(): Int = players.size

}

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.player_image
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.player_name
                    textSize = 16f
                }.lparams{
                    margin = dip(15)
                }

            }
        }
    }
}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){

    /*private val playerImage: ImageView = view.find(R.id.player_image)
    private val playerMame: TextView = view.find(R.id.player_name)

    fun bindItem(players: Player, listener: (Player) -> Unit){
        Picasso.get().load(players.strThumb).into(playerImage)
        playerMame.text = players.strPlayer
        itemView.onClick {
            listener(players)
        }
    }*/

    fun bindItem(players: Player, listener: (Player) -> Unit) {
        Picasso.get().load(players.strThumb).into(itemView.iv_icon)
        itemView.tv_name.text = players.strPlayer
        itemView.tv_position.text = players.strPosition
        itemView.tv_description.text = players.strDescriptionEN

        itemView.onClick {
            listener(players)
        }

    }
}