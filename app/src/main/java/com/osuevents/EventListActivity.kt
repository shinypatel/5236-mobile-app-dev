package com.osuevents

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.osuevents.fragment.EventListFragment
import com.osuevents.fragment.FirebaseEventListFragment
import com.osuevents.fragment.SQLiteEventListFragment
import kotlinx.android.synthetic.main.activity_event_list.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class EventListActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName

    private var dbRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)
        dbRef = FirebaseDatabase.getInstance().reference

//        Log.d(TAG, "onCreate() invoked")

        setSupportActionBar(toolbar)
        //toolbar.overflowIcon = resources.getDrawable(R.drawable.ic_more_vert_white_24dp)

        refresh()
    }

    override fun onStart() {
        super.onStart()
//        Log.d(TAG, "onStart() invoked")
    }

    override fun onResume() {
        super.onResume()
        refresh()
//        Log.d(TAG, "onResume() invoked")
    }

    override fun onPause() {
        super.onPause()
//        Log.d(TAG, "onPause() invoked")
    }

    override fun onStop() {
        super.onStop()
//        Log.d(TAG, "onStop() invoked")
    }

    override fun onDestroy() {
        super.onDestroy()
//        Log.d(TAG, "onDestroy() invoked")
    }

    private fun addFragmentsToPager() {
        val fragments: ArrayList<EventListFragment> = ArrayList()
        val titles: ArrayList<String> = ArrayList()
        var query: Query
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateToday = dateFormat.format(Date())
        val dateAfterAWeek = dateFormat.format(Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
        val dateAfterAMonth = dateFormat.format(Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000))
        Log.d(TAG, dateToday + " " + dateAfterAWeek + " " + dateAfterAMonth)

        val fragBookmarked = SQLiteEventListFragment()
        fragBookmarked.title = getString(R.string.tab_bookmarked)
        fragments.add(fragBookmarked)

        val fragToday = FirebaseEventListFragment()
        fragToday.title = getString(R.string.tab_today)
        query = dbRef!!.child("events")
                .orderByChild("start_date")
                .startAt(dateToday)
                .endAt(dateToday + "9")
        fragToday.query = query
        fragments.add(fragToday)

        val fragThisWeek = FirebaseEventListFragment()
        fragThisWeek.title = getString(R.string.tab_this_week)
        query = dbRef!!.child("events")
                .orderByChild("start_date")
                .startAt(dateToday)
                .endAt(dateAfterAWeek)
        fragThisWeek.query = query
        fragments.add(fragThisWeek)

        val fragThisMonth = FirebaseEventListFragment()
        fragThisMonth.title = getString(R.string.tab_this_month)
        query = dbRef!!.child("events")
                .orderByChild("start_date")
                .startAt(dateToday)
                .endAt(dateAfterAMonth)
        fragThisMonth.query = query
        fragments.add(fragThisMonth)

        pager.adapter = EventListFragmentPagerAdapter(supportFragmentManager, fragments, titles)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.eventlist_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_search -> {
                val intent = Intent(this.applicationContext, SearchActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_refresh -> {
                refresh()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun refresh(){
        addFragmentsToPager()
        tabs.setupWithViewPager(pager, true)
    }

}
