package com.osuevents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class EventDetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
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
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapLocation))
            startActivity(intent)
        }

        val title = view.findViewById<TextView>(R.id.event_title).text.toString()
        val calButton = view.findViewById<TextView>(R.id.calender_button)
        calButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_EDIT)
            intent.type = "vnd.android.cursor.item/event"
            intent.putExtra("eventLocation", location)
            intent.putExtra("title", title)
            startActivity(intent)
        }

        return view
    }

}
