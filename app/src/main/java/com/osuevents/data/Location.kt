package com.osuevents.data

import java.io.Serializable

class Location(): Serializable {
    var location: String? = null
    var latitude: Double? = null
    var longitude: Double? = null

    override fun toString(): String {
        if (location != null) {
            return location.toString()
        } else if (latitude != null && longitude != null) {
            return latitude.toString() + "," + longitude.toString()
        }else{
            return ""
        }
    }

}