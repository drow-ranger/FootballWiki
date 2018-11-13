package com.deonico.footballwiki.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoritesEvent.db", null, 3) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {

        db.createTable(EventDB.TABLE_EVENT, true,
            EventDB.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            EventDB.EVENT_ID to TEXT + UNIQUE,
            EventDB.EVENT_NAME to TEXT,
            EventDB.EVENT_FILENAME to TEXT,
            EventDB.EVENT_DATE to TEXT,
            EventDB.EVENT_TIME to TEXT,
            EventDB.HOME_TEAM_ID to TEXT,
            EventDB.HOME_TEAM_NAME to TEXT,
            EventDB.HOME_TEAM_SCORE to TEXT,
            EventDB.AWAY_TEAM_ID to TEXT,
            EventDB.AWAY_TEAM_NAME to TEXT,
            EventDB.AWAY_TEAM_SCORE to TEXT)

        db.createTable(TeamDB.TABLE_TEAM, true,
                TeamDB.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                TeamDB.TEAM_ID to TEXT + UNIQUE,
                TeamDB.TEAM_NAME to TEXT,
                TeamDB.STADIUM to TEXT,
                TeamDB.TEAM_YEAR to TEXT,
                TeamDB.DESCRIPTION to TEXT,
                TeamDB.TEAM_LOGO to TEXT)

        db.createTable(PlayerDB.TABLE_PLAYER, true,
            PlayerDB.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            PlayerDB.PLAYER_ID to TEXT + UNIQUE,
            PlayerDB.PLAYER_NAME to TEXT,
            PlayerDB.PLAYER_POSITION to TEXT,
            PlayerDB.PLAYER_THUMB to TEXT,
            PlayerDB.DESCRIPTION to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(EventDB.TABLE_EVENT, true)
        db.dropTable(TeamDB.TABLE_TEAM, true)
        db.dropTable(PlayerDB.TABLE_PLAYER, true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)