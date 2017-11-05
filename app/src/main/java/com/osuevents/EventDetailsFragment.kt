package com.osuevents

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.Date
import java.text.SimpleDateFormat
import java.util.*
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager



class EventDetailsFragment : Fragment() {
    val TAG: String = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val intent = activity.intent;
        val view = inflater!!.inflate(R.layout.fragment_event_details, container, false)

        val url = view.findViewById<TextView>(R.id.event_url).text.toString()
        val urlButton = view.findViewById<TextView>(R.id.url_button)
        urlButton.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }

        val location = view.findViewById<TextView>(R.id.event_location).text.toString()
        val mapLocation = "http://maps.google.co.in/maps?q=" + location
        val mapButton = view.findViewById<TextView>(R.id.map_button)
        mapButton.setOnClickListener{
            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(mapLocation))
            startActivity(mapIntent)
        }


        val title = view.findViewById<TextView>(R.id.event_title)
        title.text = intent.getStringExtra("title")

        val date = view.findViewById<TextView>(R.id.event_date)
        val startDate = intent.getStringExtra("startDate")
        val endDate = intent.getStringExtra("endDate")
        if(endDate.contains(".*\\d+.*")){
            date.text = getString(R.string.date, startDate, endDate)
        }else{
            date.text = startDate
        }

        val time = view.findViewById<TextView>(R.id.event_time)
        val allDay = intent.getStringExtra("allDay")
        val startTime = intent.getStringExtra("startTime")
        val endTime = intent.getStringExtra("endTime")
        if(allDay == "true"){
            time.text = getString(R.string.all_day)
        }else if(startTime != endTime){
            time.text = getString(R.string.time, startTime, endTime)
        }

        val startDateAndTimeStr = intent.getStringExtra("startDateAndTime")
        val startDateAndTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(startDateAndTimeStr)
        val startTimeCal = Calendar.getInstance()
        startTimeCal.time = startDateAndTime

        val endDateAndTimeStr = intent.getStringExtra("endDateAndTime")
        val endDateAndTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(endDateAndTimeStr)
        val endTimeCal = Calendar.getInstance()
        endTimeCal.time = endDateAndTime

        val calButton = view.findViewById<TextView>(R.id.calender_button)
        calButton.setOnClickListener{
            val calIntent = Intent(Intent.ACTION_EDIT)
            calIntent.type = "vnd.android.cursor.item/event"
            calIntent.putExtra("eventLocation", location)
            calIntent.putExtra("title", title.text.toString())
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTimeCal.timeInMillis)
            if(allDay == "true"){
                calIntent.putExtra("allDay", true)
            }else{
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTimeCal.timeInMillis)
            }
            startActivity(calIntent)
        }

        return view
    }

}
