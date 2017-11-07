package com.osuevents

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_search.*
import java.text.SimpleDateFormat
import java.util.*


class SearchActivity : AppCompatActivity() {
    val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)

        val startDateCal = Calendar.getInstance()
        val fromDateView = findViewById<EditText>(R.id.from_date)
//        fromDateView.setText(SimpleDateFormat(getString(R.string.search_date_format)).format(Date()))
        val fromDate = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            startDateCal.set(Calendar.YEAR, year)
            startDateCal.set(Calendar.MONTH, monthOfYear)
            startDateCal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            fromDateView.setText(SimpleDateFormat(getString(R.string.search_date_format)).format(startDateCal.time))
        }

        fromDateView.setOnClickListener({
            DatePickerDialog(this, fromDate, startDateCal
                    .get(Calendar.YEAR), startDateCal.get(Calendar.MONTH),
                    startDateCal.get(Calendar.DAY_OF_MONTH)).show()
        })

        val toDateCal = Calendar.getInstance()
        val toDateView = findViewById<EditText>(R.id.to_date)
//        toDateView.setText(SimpleDateFormat(getString(R.string.search_date_format)).format(Date()))
        val toDate = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            toDateCal.set(Calendar.YEAR, year)
            toDateCal.set(Calendar.MONTH, monthOfYear)
            toDateCal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            toDateView.setText(SimpleDateFormat(getString(R.string.search_date_format)).format(toDateCal.time))
        }

        toDateView.setOnClickListener({
            DatePickerDialog(this, toDate, toDateCal
                    .get(Calendar.YEAR), toDateCal.get(Calendar.MONTH),
                    toDateCal.get(Calendar.DAY_OF_MONTH)).show()
        })

        btn_search.setOnClickListener { view ->
            var intent: Intent = Intent(applicationContext, SearchResultsActivity::class.java)
            intent.putExtra("keyword", keywords.text)
            intent.putExtra("location", location.text)
            intent.putExtra("from_date", fromDateView.text)
            intent.putExtra("to_date", toDateView.text)
            startActivity(intent)
        }
    }


}
