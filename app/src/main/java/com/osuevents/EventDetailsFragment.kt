package com.osuevents

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class EventDetailsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_event_details, container, false)

        val intent = activity.intent;
        val url = view.findViewById<TextView>(R.id.event_url).text.toString()
        val urlButton = view.findViewById<TextView>(R.id.url_button)
        urlButton.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }


        val eventLoc = view.findViewById<TextView>(R.id.event_location).text.toString()
        val mapButton = view.findViewById<TextView>(R.id.map_button)
        mapButton.setOnClickListener{
            if (ContextCompat.checkSelfPermission(activity.applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions()
            }else {
                val lm = activity.applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val currentLoc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                val lat = currentLoc.latitude
                val lng = currentLoc.longitude
                val loc = "http://maps.google.com/maps?saddr:$lat,$lng&daddr=$eventLoc"
                val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(loc))
                startActivity(mapIntent)
            }
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
            if (ContextCompat.checkSelfPermission(activity.applicationContext,
                    Manifest.permission.READ_CALENDAR)
                    != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(activity.applicationContext,
                            Manifest.permission.WRITE_CALENDAR)
                            != PackageManager.PERMISSION_GRANTED) {
                requestPermissions()
            }else {
                val calIntent = Intent(Intent.ACTION_EDIT)
                calIntent.type = "vnd.android.cursor.item/event"
                calIntent.putExtra("eventLocation", eventLoc)
                calIntent.putExtra("title", title.text.toString())
                calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTimeCal.timeInMillis)
                if (allDay == "true") {
                    calIntent.putExtra("allDay", true)
                } else {
                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTimeCal.timeInMillis)
                }
                startActivity(calIntent)
            }
        }

        return view
    }

    fun requestPermissions(){
        var permissions = arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR,
                Manifest.permission.ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(activity, permissions, 1)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                }
                return
            }
        }
    }

}


