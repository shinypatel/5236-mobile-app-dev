package com.osuevents.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.Query
import com.osuevents.R
import com.osuevents.data.Event

class FirebaseEventListFragment : EventListFragment() {
    val TAG: String = javaClass.simpleName

    var query: Query? = null
    var recyclerAdapter: FirebaseRecyclerAdapter<Event, EventListItemViewHolder>? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var options = FirebaseRecyclerOptions.Builder<Event>().setQuery(query, Event::class.java).build()
        recyclerAdapter = object : FirebaseRecyclerAdapter<Event, EventListItemViewHolder>(options) {
            override fun onBindViewHolder(holder: EventListItemViewHolder?, position: Int, model: Event) {
                holder?.bind(model)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListItemViewHolder {
                var view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_event_list_item, parent, false)
                return EventListItemViewHolder(view)
            }
        }
        recyclerView?.adapter = recyclerAdapter
    }

    override fun onStart() {
        super.onStart()
        recyclerAdapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        recyclerAdapter?.stopListening()
    }
}
