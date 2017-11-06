package com.osuevents.data

import java.io.Serializable

class Event(): Serializable {
    var id: Int? = null
    var title: String? = null
    var start_date: String? = null
    var end_date: String? = null
    var all_day: Boolean? = false
    var link: String? = null
    var content: String? = null
    var location: Location? = null
}
