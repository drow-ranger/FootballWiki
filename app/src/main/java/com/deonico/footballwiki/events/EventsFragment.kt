package com.deonico.footballwiki.events

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.*
import android.widget.SearchView
import com.deonico.footballwiki.R
import com.deonico.footballwiki.R.layout.fragment_events
import kotlinx.android.synthetic.main.fragment_events.*
import org.jetbrains.anko.support.v4.ctx

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
                0 -> return EventsPreviousFragment()
                1 -> return EventsPreviousFragment()

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



}

/*
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
                    val intent = Intent(context, EventsSearchActivity::class.java)
                    startActivity(intent)
                }
            }

            true
        }
    }

    private fun setupViewPager(viewPager: ViewPager){
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFrag(EventsListFragment.newFragment(2, leagueId), "NEXT")
        adapter.addFrag(EventsListFragment.newFragment(1, leagueId), "LAST")
        viewPager.adapter = adapter
    }

}*/
