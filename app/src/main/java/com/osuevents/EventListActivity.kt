package com.osuevents

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_event_list.*

class EventListActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName

    private var fragments: ArrayList<EventListFragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)
        fragments.add(EventListFragment())
        fragments.add(EventListFragment())
        fragments.add(EventListFragment())
        fragments.add(EventListFragment())
        pager.adapter = EventListFragmentPagerAdapter(supportFragmentManager, fragments)
        //tabs.setupWithViewPager(pager, true)
        var tabs = findViewById<View>(R.id.tabs)
    }
}
