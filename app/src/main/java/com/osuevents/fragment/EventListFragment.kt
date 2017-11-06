package com.osuevents.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.osuevents.EventDetailsActivity
import com.osuevents.R
import com.osuevents.data.Event
import com.osuevents.dummy.DummyContent.DummyItem
import java.text.SimpleDateFormat
import java.util.*

open class EventListFragment : Fragment() {
    private val TAG: String = javaClass.simpleName

    private var mListener: OnListFragmentInteractionListener? = null
    var title: String = ""
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Log.d(TAG, "onCreate() invoked on fragment " + title)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        Log.d(TAG, "onCreateView() invoked on fragment " + title)
        val root = inflater!!.inflate(R.layout.fragment_event_list, container, false)
        recyclerView = root?.findViewById<RecyclerView>(R.id.list)
        recyclerView?.hasFixedSize()
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        return root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
//        Log.d(TAG, "onAttach() invoked on fragment " + title)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
//        Log.d(TAG, "onDetach() invoked on fragment " + title)
        mListener = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        Log.d(TAG, "onActivityCreated() invoked on fragment " + title)
    }

    override fun onStart() {
        super.onStart()
//        Log.d(TAG, "onStart() invoked on fragment " + title)
    }

    override fun onResume() {
        super.onResume()
//        Log.d(TAG, "onResume() invoked on fragment " + title)
    }

    override fun onPause() {
        super.onPause()
//        Log.d(TAG, "onPause() invoked on fragment " + title)
    }

    override fun onStop() {
        super.onStop()
//        Log.d(TAG, "onStop() invoked on fragment " + title)
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        Log.d(TAG, "onDestroyView() invoked on fragment " + title)
    }

    override fun onDestroy() {
        super.onDestroy()
//        Log.d(TAG, "onDestroyView() invoked on fragment " + title)
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
                intent.putExtra("url", event.link.toString())
                intent.putExtra("location", event.location.toString())
                startActivity(intent)
            })
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: DummyItem)
    }
}
