package com.deonico.footballwiki.events

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.deonico.footballwiki.R
import com.deonico.footballwiki.R.layout.fragment_events
import com.deonico.footballwiki.R.id.search_menu
import com.deonico.footballwiki.events.next.EventNextFragment
import com.deonico.footballwiki.events.previous.EventPreviousFragment
import com.deonico.footballwiki.teams.detail.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_events.*
import org.jetbrains.anko.support.v4.find

/*
class EventsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(fragment_events, container, false)
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
                // Switch to view for this tab
                viewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    class PagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return EventNextFragment()
                1 -> return EventPreviousFragment()

                else -> return null
            }
        }

        override fun getCount(): Int {
            return 2;
        }

        private val tabTitles = arrayOf("Next", "Previous")

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitles[position]
        }

    }



}*/

class EventsFragment: Fragment(){
    private var leagueId = "4331"   //EPL

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViewPager(viewPager)

        event_tabs.setupWithViewPager(viewPager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(fragment_events, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = find<Toolbar>(R.id.event_toolbar)
        toolbar.inflateMenu(R.menu.search)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                search_menu -> {
                    val intent = Intent(context, MatchesListSearchActivity::class.java)
                    startActivity(intent)
                }
            }

            true
        }
    }

    private fun setupViewPager(viewPager: ViewPager){
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFrag(EventsFragment.newFragment(2, leagueId), "NEXT")
        adapter.addFrag(EventsFragment.newFragment(1, leagueId), "LAST")
        viewPager.adapter = adapter
    }

}