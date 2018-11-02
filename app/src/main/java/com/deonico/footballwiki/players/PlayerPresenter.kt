package com.deonico.footballwiki.players

import android.util.Log
import com.deonico.footballwiki.api.ApiRepository
import com.deonico.footballwiki.api.TheSportDBApi
import com.deonico.footballwiki.model.PlayerResponse
import com.deonico.footballwiki.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter(private val view: PlayerView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerList(idTeam: String?) {
        view.showLoading()
        Log.d("presenter", "masuk presenter" + idTeam)
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getPlayers(idTeam)),
                    PlayerResponse::class.java
                )
            }
            Log.d("tesdulu", data.await().player[0].strPlayer)
            view.hideLoading()
            view.showPlayerList(data.await().player)

        }
    }

}