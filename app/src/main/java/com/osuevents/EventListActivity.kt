package com.osuevents

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_event_list.*

class EventListActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName

    private var fragments: ArrayList<EventListFragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        var fragBookmarked = EventListFragment()
        fragBookmarked.title = getString(R.string.tab_bookmarked)
        fragments.add(fragBookmarked)

        var fragToday = EventListFragment()
        fragToday.title = getString(R.string.tab_today)
        fragments.add(fragToday)

        var fragThisWeek = EventListFragment()
        fragThisWeek.title = getString(R.string.tab_this_week)
        fragments.add(fragThisWeek)

        var fragThisMonth = EventListFragment()
        fragThisMonth.title = getString(R.string.tab_this_month)
        fragments.add(fragThisMonth)

        pager.adapter = EventListFragmentPagerAdapter(supportFragmentManager, fragments)
        tabs.setupWithViewPager(pager, true)
    }
}
