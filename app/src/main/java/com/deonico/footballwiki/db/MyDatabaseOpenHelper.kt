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
                /*EventDB.EVENT_NAME to TEXT,
                EventDB.EVENT_DATE to TEXT,*//*
                EventDB.EVENT_TIME to TEXT,
                EventDB.HOME_LOGO to TEXT,
                EventDB.AWAY_LOGO to TEXT,*//*
                EventDB.HOME_TEAM to TEXT,
                EventDB.AWAY_TEAM to TEXT,
                EventDB.HOME_SCORE to TEXT,
                EventDB.AWAY_SCORE to TEXT,*//*
                EventDB.HOME_GOAL to TEXT,
                EventDB.AWAY_GOAL to TEXT,
                EventDB.HOME_GK to TEXT,
                EventDB.AWAY_GK to TEXT,
                EventDB.HOME_DEF to TEXT,
                EventDB.AWAY_DEF to TEXT,
                EventDB.HOME_MDF to TEXT,
                EventDB.AWAY_MDF to TEXT,
                EventDB.HOME_FW to TEXT,
                EventDB.AWAY_FW to TEXT,
                EventDB.HOME_SUB to TEXT,
                EventDB.AWAY_SUB to TEXT,*//*
                EventDB.ID_HOME to TEXT,
                EventDB.ID_AWAY to TEXT)*/
                EventDB.EVENT_DATE to TEXT,
                EventDB.HOME_TEAM to TEXT,
                EventDB.AWAY_TEAM to TEXT,
                EventDB.HOME_SCORE to TEXT,
                EventDB.AWAY_SCORE to TEXT,
                EventDB.HOME_SHOTS to TEXT,
                EventDB.AWAY_SHOTS to TEXT,
                EventDB.HOME_GOAL to TEXT,
                EventDB.AWAY_GOAL to TEXT,
                EventDB.HOME_YELLOW to TEXT,
                EventDB.AWAY_YELLOW to TEXT,
                EventDB.HOME_RED to TEXT,
                EventDB.AWAY_RED to TEXT,
                EventDB.ID_HOME to TEXT,
                EventDB.ID_AWAY to TEXT)

        db.createTable(TeamDB.TABLE_TEAM, true,
                TeamDB.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                TeamDB.TEAM_ID to TEXT + UNIQUE,
                TeamDB.TEAM_NAME to TEXT,
                TeamDB.STADIUM to TEXT,
                TeamDB.TEAM_YEAR to TEXT,
                TeamDB.DESCRIPTION to TEXT,
                TeamDB.TEAM_LOGO to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(EventDB.TABLE_EVENT, true)
        db.dropTable(TeamDB.TABLE_TEAM, true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)