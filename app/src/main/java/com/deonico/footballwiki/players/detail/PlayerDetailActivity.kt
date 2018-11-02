package com.deonico.footballwiki.players.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.deonico.footballwiki.R
import com.deonico.footballwiki.model.Player
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_detail.*

class PlayerDetailActivity : AppCompatActivity() {
    private lateinit var player: Player

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        setSupportActionBar(toolbar)
        //supportActionBar?.hide()

        player = intent.getParcelableExtra("playerObject")
        fillData()

    }

    private fun fillData(){
        tv_position.text = player.strPosition
        tv_weight.text = player.strWeight
        tv_height.text = player.strHeight
        tv_overview.text = player.strDescriptionEN
        tv_signing.text = player.strSigning
        iv_facebook.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://"+player.strFacebook))
            startActivity(i)
        }
        iv_twitter.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://"+player.strTwitter))
            startActivity(i)
        }
        iv_instagram.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://"+player.strInstagram))
            startActivity(i)
        }

        supportActionBar?.title = player.strPlayer
        Glide.with(this).
            load(player.strFanart1).
            apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_nodata)
                    .error(R.drawable.ic_nodata)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)).
            into(iv_banner)
    }
    
}
