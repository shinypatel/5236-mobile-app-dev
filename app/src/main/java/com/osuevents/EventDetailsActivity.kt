package com.osuevents

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class EventDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_event_details, EventDetailsFragment())
                    .commit()
        }

    }
}
