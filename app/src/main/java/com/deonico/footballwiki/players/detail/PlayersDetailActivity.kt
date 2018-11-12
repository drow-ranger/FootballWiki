package com.deonico.footballwiki.players.detail

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.deonico.footballwiki.R
import com.deonico.footballwiki.db.PlayerDB
import com.deonico.footballwiki.db.database
import com.deonico.footballwiki.model.Player
import kotlinx.android.synthetic.main.activity_player_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class PlayersDetailActivity : AppCompatActivity() {
    private lateinit var player: Player

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        
        //setSupportActionBar(toolbar)
        //supportActionBar?.hide()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        player = intent.getParcelableExtra("playerData")
        favoriteState()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuItem = menu
        setFavorite()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if(isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(
                    PlayerDB.TABLE_PLAYER,
                    PlayerDB.PLAYER_ID to player.idPlayer,
                    PlayerDB.PLAYER_NAME to player.strPlayer,
                    PlayerDB.PLAYER_POSITION to player.strPosition,
                    PlayerDB.PLAYER_THUMB to player.strThumb,
                    PlayerDB.DESCRIPTION to player.strDescriptionEN)
            }
            Snackbar.make(player_detail_viewpager,"Added to favorite", Snackbar.LENGTH_LONG).show()
        }catch (e: SQLiteConstraintException){
            Snackbar.make(player_detail_viewpager,e.localizedMessage, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use{
                delete(PlayerDB.TABLE_PLAYER, "(PLAYER_ID = {id})", "id" to player.idPlayer.orEmpty())
            }
            Snackbar.make(player_detail_viewpager,"Removed to favorite", Snackbar.LENGTH_LONG).show()
        }catch (e: SQLiteConstraintException){
            Snackbar.make(player_detail_viewpager,e.localizedMessage, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setFavorite(){
        val icon = if(isFavorite) R.drawable.ic_added_to_favorite else R.drawable.ic_add_to_favorite

        menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, icon)
    }

    private fun favoriteState(){
        database.use {
            val result = select(PlayerDB.TABLE_PLAYER)
                .whereArgs("(PLAYER_ID = {id})", "id" to player.idPlayer.orEmpty())
            val favorite = result.parseList(
                classParser<PlayerDB
                        >()
            )
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
    
}
