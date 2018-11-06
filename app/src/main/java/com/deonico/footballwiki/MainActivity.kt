package com.deonico.footballwiki

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.widget.Toast
import com.deonico.footballwiki.R.id.*
import com.deonico.footballwiki.R.menu.navigation
import com.deonico.footballwiki.teams.TeamsFragment
import com.deonico.footballwiki.events.EventsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //private var savedInstanceState: Bundle? = null

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

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_events -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content_main, EventsFragment(), EventsFragment::class.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_teams -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.content_main, TeamsFragment(), TeamsFragment::class.simpleName)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content_main, EventsFragment(), EventsFragment::class.simpleName)
            .commit()

        nav_button.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    /*private fun openFragment(fragment: Fragment){
        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_main, fragment, fragment.javaClass.simpleName)
                .commit()
        }
    }*/

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
