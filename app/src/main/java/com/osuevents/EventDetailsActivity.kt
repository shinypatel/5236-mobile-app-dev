package com.osuevents

//import com.osuevents.db.EventSQLite
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.osuevents.data.Event
import com.osuevents.db.DatabaseHandler
import kotlinx.android.synthetic.main.activity_event_list.*

class EventDetailsActivity : AppCompatActivity() {
    val TAG = javaClass.simpleName
    var bookmarked: Boolean? = null
    var dbHandler: DatabaseHandler? = null
    var event: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        setSupportActionBar(toolbar)
        dbHandler = DatabaseHandler(this)
        event = intent.getSerializableExtra("event") as Event
        bookmarked = dbHandler?.getEvent(event?.id as Int) != null

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_event_details, EventDetailsFragment())
                    .commit()
        }

        Log.d(TAG, "End of onCreate")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.eventdetails_toolbar, menu)

        if(bookmarked == true) {
            menu!!.findItem(R.id.action_bookmark).icon = getDrawable(R.drawable.ic_bookmark_white_24dp)
        } else {
            menu!!.findItem(R.id.action_bookmark).icon = getDrawable(R.drawable.ic_bookmark_border_white_24dp)
        }
        Log.d(TAG, "Event id = " + event?.id)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_bookmark -> {
                if(bookmarked == true) {
                    bookmarked = false
                    item.icon = getDrawable(R.drawable.ic_bookmark_border_white_24dp)
                    dbHandler?.removeEvent(event?.id)
                    Toast.makeText(this, R.string.bookmark_removed, Toast.LENGTH_SHORT).show()
                } else {
                    bookmarked = true
                    item.icon = getDrawable(R.drawable.ic_bookmark_white_24dp)
                    dbHandler?.addEvent(event)
                    Toast.makeText(this, R.string.bookmark_added, Toast.LENGTH_SHORT).show()
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
