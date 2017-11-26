package com.osuevents

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.osuevents.data.Event
import com.osuevents.fragment.FirebaseEventListFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class SearchResultsActivity : AppCompatActivity() {
    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

//        Log.d(TAG, "onCreate() invoked")

        if(Utility.isNetworkAvailable(this)){
            val fragment = FirebaseEventListFragment()
            fragment.query = buildQuery()

            if (savedInstanceState == null) {
                supportFragmentManager
                        .beginTransaction()
                        .add(R.id.fragment_search_results, fragment)
                        .commit()
            }
        }else{
            Toast.makeText(this, R.string.internet_connection, Toast.LENGTH_LONG).show()
        }
    }

    fun buildQuery(): Query {
        val dbRef = FirebaseDatabase.getInstance().reference

        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val fromDate = dateFormat.format(dateFormat.parse(intent.getStringExtra("from_date")))
        val toDate = dateFormat.format(dateFormat.parse(intent.getStringExtra("to_date")))

        val query = dbRef!!.child("events")
                .orderByChild("start_date")
                .startAt(fromDate)
                .endAt(toDate + "9")

        return query
    }

    fun getEventList(query: Query): ArrayList<Event> {
        val list: ArrayList<Event> = ArrayList()
        val listener: ValueEventListener = object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.e(TAG, "Error fetching search results")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(TAG, "" + snapshot.childrenCount + " results found")

                for(data in snapshot.children) {
                    val event: Event? = data.getValue(Event::class.java)
//                    var title = event?.title
//                    var content = event?.content
//                    var loc = event?.location?.location

                    if(event != null) {// && title?.indexOf(intent.getStringExtra("keyword")) != -1
                        // && content?.indexOf(intent.getStringExtra("keyword")) != -1
                        // && loc?.indexOf(intent.getStringExtra("location")) != -1) {
                        list.add(event)
                    }
                }
            }
        }
        query.addValueEventListener(listener)

        return list
    }


    override fun onStart() {
        super.onStart()
//        Log.d(TAG, "onStart() invoked")
    }

    override fun onResume() {
        super.onResume()
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

}
