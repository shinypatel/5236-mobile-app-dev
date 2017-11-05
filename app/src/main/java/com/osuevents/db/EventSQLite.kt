package com.osuevents.db

import com.osuevents.data.Event

class EventSQLite (){
    var id: Int? = null
    var title: String? = null
    var start_date: String? = null
    var end_date: String? = null
    var all_day: Int? = null
    var link: String? = null
    var content: String? = null
    var location: String? = null
    var latitude: Double? = null
    var longitude: Double? = null

    constructor (event: Event): this() {
        id = event.id
        title = event.title
        start_date = event.start_date
        end_date = event.end_date
        all_day = if (event.all_day == true) 1 else 0
        link = event.link
        content = event.content
        location = event.location?.location
        latitude = event.location?.latitude
        longitude = event.location?.longitude
    }
}