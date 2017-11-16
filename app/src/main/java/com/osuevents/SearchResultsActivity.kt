package com.osuevents

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.database.*
import com.osuevents.data.Event
import com.osuevents.fragment.ArrayListEventListFragment

class SearchResultsActivity : AppCompatActivity() {
    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        var fragment = ArrayListEventListFragment()
        fragment.eventList = getEventList(buildQuery())

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_search_results, fragment)
                    .commit()
        }
    }

    fun buildQuery(): Query {
        var dbRef = FirebaseDatabase.getInstance().reference
        var query = dbRef.child("events").orderByChild("start_date")

        if(intent.getStringExtra("from_date") != "") {
            query = query.startAt(intent.getStringExtra("from_date"))
        }
        if(intent.getStringExtra("to_date") != "") {
            query = query.endAt(intent.getStringExtra("to_date"))
        }

        return query
    }

    fun getEventList(query: Query): ArrayList<Event> {
        var list: ArrayList<Event> = ArrayList()
        var listener: ValueEventListener = object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.e(TAG, "Error fetching search results")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(TAG, "" + snapshot.childrenCount + " results found")

                for(data in snapshot.children) {
                    var event: Event? = data.getValue(Event::class.java)
//                    var title = event?.title
//                    var content = event?.content
//                    var loc = event?.location?.location

                    if(event != null) {// && title?.indexOf(intent.getStringExtra("keyword")) != -1
                        //    && content?.indexOf(intent.getStringExtra("keyword")) != -1
                          //  && loc?.indexOf(intent.getStringExtra("location")) != -1) {
                        list.add(event)
                    }
                }
            }
        }
        query.addValueEventListener(listener)

        return list
    }
}
