package com.osuevents.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.osuevents.data.Event
import com.osuevents.data.Location

class DatabaseHandler (context: Context): SQLiteOpenHelper (context, "EVENTS_DB", null, 1){
    val TABLE_EVENT: String = "events"
    val ID: String = "id"
    val TITLE: String = "title"
    val START_DATE: String = "start_date"
    val END_DATE: String = "end_date"
    val ALL_DAY: String = "all_day"
    val LINK: String = "link"
    val CONTENT: String = "content"
    val LOCATION: String = "location"
    val LATITUDE: String = "latitude"
    val LONGITUDE: String = "longitude"

    override fun onCreate(db: SQLiteDatabase?) {
        var CREATE_TABLE: String = "CREATE TABLE " + TABLE_EVENT + "(" +
                ID + " INTEGER PRIMARY KEY, " +
                TITLE + " TEXT, " +
                START_DATE + " TEXT, " +
                END_DATE + " TEXT, " +
                ALL_DAY + " INTEGER, " +
                LINK + " TEXT, " +
                CONTENT + " TEXT, " +
                LOCATION + " TEXT, " +
                LATITUDE + " REAL, " +
                LONGITUDE + " REAL" +
                ")"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT)
        onCreate(db)
    }

    fun addEvent(event: EventSQLite) {
        var values = ContentValues()
        values.put(ID, event.id)
        values.put(TITLE, event.title)
        values.put(START_DATE, event.start_date)
        values.put(END_DATE, event.end_date)
        values.put(ALL_DAY, event.all_day)
        values.put(LINK, event.link)
        values.put(CONTENT, event.content)
        values.put(LOCATION, event.location)
        values.put(LATITUDE, event.latitude)

        writableDatabase.insert(TABLE_EVENT, null, values)
        writableDatabase.close()
    }

    fun getEvent(id: Int): EventSQLite {
        var cursor = readableDatabase.query(TABLE_EVENT, arrayOf(ID, TITLE, START_DATE, END_DATE, ALL_DAY, LINK, CONTENT, LOCATION, LATITUDE, LONGITUDE),
                ID + "=?", arrayOf("" + id), null, null, null)
        if(cursor != null) {
            cursor.moveToFirst()
        }

        var event = EventSQLite()
        return event
    }

    fun getAllEvents(): ArrayList<Event> {
        var allEvents = ArrayList<Event>()
        var cursor = readableDatabase.rawQuery("SELECT * FROM " + TABLE_EVENT, null)

        if(cursor.moveToFirst()) {
            do {
                var event = Event()
                event.id = cursor.getInt(0)
                event.title = cursor.getString(1)
                event.start_date = cursor.getString(2)
                event.end_date = cursor.getString(3)
                event.all_day = cursor.getInt(4) == 1
                event.link = cursor.getString(5)
                event.content = cursor.getString(6)
                event.location = Location()
                event.location?.location = cursor.getString(7)
                event.location?.latitude = cursor.getDouble(8)
                event.location?.longitude = cursor.getDouble(9)

                allEvents.add(event)
            } while (cursor.moveToNext())
        }
        return allEvents
    }
}
