package com.osuevents

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_event_list.*

class EventListActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName

    private var fragments: ArrayList<EventListFragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        setSupportActionBar(toolbar)
        //toolbar.overflowIcon = resources.getDrawable(R.drawable.ic_more_vert_white_24dp)

        addFragmentsToPager()
        tabs.setupWithViewPager(pager, true)
    }

    private fun addFragmentsToPager() {
        var fragBookmarked = EventListFragment()
        fragBookmarked.title = getString(R.string.tab_bookmarked)
        fragments.add(fragBookmarked)

        var fragToday = EventListFragment()
        fragToday.title = getString(R.string.tab_today)
        fragments.add(fragToday)
54
        var fragThisWeek = EventListFragment()
        fragThisWeek.title = getString(R.string.tab_this_week)
        fragments.add(fragThisWeek)

        var fragThisMonth = EventListFragment()
        fragThisMonth.title = getString(R.string.tab_this_month)
        fragments.add(fragThisMonth)

        pager.adapter = EventListFragmentPagerAdapter(supportFragmentManager, fragments)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.eventlist_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_search -> TODO()
            R.id.action_refresh -> TODO()
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
