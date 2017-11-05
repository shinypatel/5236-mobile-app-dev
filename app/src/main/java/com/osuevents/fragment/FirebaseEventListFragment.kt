package com.osuevents.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.Query
import com.osuevents.EventDetailsActivity
import com.osuevents.R
import com.osuevents.data.Event
import java.text.SimpleDateFormat
import java.util.*

class FirebaseEventListFragment : EventListFragment() {
    val TAG: String = javaClass.simpleName

    var query: Query? = null
    var recyclerView: RecyclerView? = null
    var recyclerAdapter: FirebaseRecyclerAdapter<Event, EventListItemViewHolder>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root = super.onCreateView(inflater, container, savedInstanceState)
        recyclerView = root?.findViewById<RecyclerView>(R.id.list)
        recyclerView?.hasFixedSize()
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        return root
    }

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

    inner class EventListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleView: TextView = itemView.findViewById<TextView>(R.id.text_event_title)
        var dateFromView: TextView = itemView.findViewById<TextView>(R.id.text_date_from)
        var dateToView: TextView = itemView.findViewById<TextView>(R.id.text_date_to)
        var timesView: LinearLayout = itemView.findViewById<LinearLayout>(R.id.text_times)
        var fromView: TextView = itemView.findViewById<TextView>(R.id.text_from)
        var toView: TextView = itemView.findViewById<TextView>(R.id.text_to)
        var allDayView: TextView = itemView.findViewById<TextView>(R.id.text_all_day)

        fun bind(event: Event) {
            titleView.text = event.title
            var startDate: Date = SimpleDateFormat(getString(R.string.long_date_format)).parse(event.start_date)
            var endDate: Date? = if(event.end_date != null) SimpleDateFormat(getString(R.string.long_date_format)).parse(event.end_date) else null
            var allDay = event.all_day

            dateFromView.text = SimpleDateFormat(getString(R.string.short_date_format)).format(startDate)
            if(allDay == true) {
                allDayView.visibility = View.VISIBLE
                timesView.visibility = View.INVISIBLE
                dateToView.visibility = View.VISIBLE
                dateToView.text = if(endDate != null && endDate.after(startDate)) SimpleDateFormat(getString(R.string.short_date_format)).format(endDate) else ""
            } else {
                allDayView.visibility = View.INVISIBLE
                dateToView.visibility = View.INVISIBLE
                timesView.visibility = View.VISIBLE
                fromView.text = SimpleDateFormat(getString(R.string.time_format)).format(startDate)
                toView.text = if(endDate != null && endDate.after(startDate)) "- " + SimpleDateFormat(getString(R.string.time_format)).format(endDate) else ""
            }

            itemView.setOnClickListener({
                val intent = Intent(activity.applicationContext, EventDetailsActivity::class.java)
                intent.putExtra("title", event.title)
                intent.putExtra("startDateAndTime", event.start_date.toString())
                intent.putExtra("endDateAndTime", event.end_date.toString())
                intent.putExtra("allDay", allDay.toString())
                intent.putExtra("startDate", dateFromView.text.toString())
                intent.putExtra("endDate", dateToView.text.toString())
                intent.putExtra("startTime", fromView.text.toString())
                intent.putExtra("endTime", toView.text.toString())

                intent.putExtra("content", event.content.toString())
                intent.putExtra("link", event.link.toString())
                intent.putExtra("location", event.location.toString())
                startActivity(intent)
            })
        }

    }
}
