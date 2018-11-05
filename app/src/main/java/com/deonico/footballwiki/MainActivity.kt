package com.deonico.footballwiki

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.widget.Toast
import com.deonico.footballwiki.R.id.*
import com.deonico.footballwiki.teams.TeamsFragment
import com.deonico.footballwiki.events.EventsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var savedInstanceState: Bundle? = null

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedInstanceState = savedInstanceState
        setContentView(R.layout.activity_main)

        nav_button.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_events -> {
                    openFragment(EventsFragment())
                    true
                }
                R.id.navigation_teams ->{
                    openFragment(TeamsFragment())
                    true
                }
                else -> false
            }
        }
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedInstanceState = savedInstanceState
        setContentView(R.layout.activity_main)

        nav_button.setOnNavigationItemSelectedListener {
                item -> when(item.itemId){
            navigation_events -> {
                supportActionBar?.hide()
                openFragment(EventsFragment())
                return@setOnNavigationItemSelectedListener true
            }
            navigation_teams -> {
                supportActionBar?.show()
                openFragment(TeamsFragment())
                return@setOnNavigationItemSelectedListener true
            }
            navigation_favorite -> {
                supportActionBar?.hide()
                //openFragment(FavoritesFragment())
                return@setOnNavigationItemSelectedListener true
            }
        }
            false
        }

        nav_button.selectedItemId = navigation_teams
    }

    private fun openFragment(fragment: Fragment){
        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_main, fragment, fragment.javaClass.simpleName)
                .commit()
        }
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

}
