package com.deonico.footballwiki.favorites

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.deonico.footballwiki.R
import com.deonico.footballwiki.R.layout.fragment_favorites
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)

        return inflater.inflate(fragment_favorites, container, false)
    }

    private lateinit var pagerAdapter: PagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter = PagerAdapter(fragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    class PagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return FavoriteEventsFragment()
                1 -> return FavoriteTeamsFragment()
                2 -> return FavoritePlayersFragment()

                else -> return null
            }
        }

        override fun getCount(): Int {
            return 3
        }

        private val tabTitles = arrayOf("Events", "Teams", "Players")

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitles[position]
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.searchMenu)?.setVisible(false)
        menu?.findItem(R.id.searchMenu1)?.setVisible(false)
        super.onPrepareOptionsMenu(menu)
    }

}