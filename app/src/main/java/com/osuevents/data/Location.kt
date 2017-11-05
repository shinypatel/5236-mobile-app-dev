package com.osuevents.data

class Location() {
    var location: String? = null
    var latitude: Double? = null
    var longitude: Double? = null

    override fun toString(): String {
        return "{ loc: \"" + location + "\", lat: " + latitude + ", lon:  " + longitude + " }"
    }
}