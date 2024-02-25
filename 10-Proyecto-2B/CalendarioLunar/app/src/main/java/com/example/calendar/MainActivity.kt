package com.example.calendar

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.TextView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var dateTV: TextView
    lateinit var calendarView: CalendarView
    lateinit var moonShape : TextView
    lateinit var nextPage : Button
    lateinit var saveEvent : Button
    lateinit var glosarioButton : Button
    lateinit var events : TextView
    lateinit var eventName : TextView
    lateinit var dtc: String
    var dateMessage: String? = null
    lateinit var db: FirebaseFirestore
    lateinit var eventsCollection: CollectionReference


    val eventlist= mutableMapOf<String,String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // initializing variables of
        // list view with their ids.
        var sendDate = "11/8/1999"
        dateTV = findViewById(R.id.idTVDate)
        calendarView = findViewById(R.id.calendarView)
        moonShape = findViewById(R.id.phaseView)
        nextPage = findViewById(R.id.moonDisplay)
        saveEvent = findViewById(R.id.saveEvent)
        events= findViewById(R.id.MostarEvento)
        eventName = findViewById(R.id.eventName)
        glosarioButton = findViewById(R.id.glosario)
        db = FirebaseFirestore.getInstance()
        eventsCollection = db.collection("events")


        val monthMap = arrayOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
        val moonPhases = arrayOf("Luna Nueva", "Creciente Creciente", "Primer Cuarto Creciente", "Gibbosa Creciente", "Luna Llena", "Gibbosa Menguante", "Último Cuarto Creciente", "Creciente Menguante")

        val calen=Calendar.getInstance()
        calendarView
            .setOnDateChangeListener(
                OnDateChangeListener { view, year, month, dayOfMonth ->
                   val Date = (dayOfMonth.toString() + "-"
                            + monthMap[month] + "-" + year)
                    sendDate = Date
                    val sendDate = (dayOfMonth.toString() + "/"
                            + (month+1) + "/" + year)
                    numOfDays(sendDate, moonPhases)
                    dateTV.setText(Date)
                    events.text=eventlist[Date]

                })
        nextPage.setOnClickListener {
            intent = Intent(this, moonDisplay::class.java)
            val bundle = Bundle()
            bundle.putString("date", sendDate)
            bundle.putString("phaseName", moonShape.text.toString())
            intent.putExtras(bundle)
            startActivity(intent)
        }

        saveEvent.setOnClickListener {
            val txt = eventName.text.toString()
            val dt = dateTV.text.toString()

            saveEventToFirestore(dt, txt)

            eventlist[dt] = txt
            events.text = txt
            eventName.text = ""

        }

        glosarioButton.setOnClickListener {
            val intent = Intent(this, GlosarioActivity::class.java)
            startActivity(intent)
        }

    }

    fun numOfDays(sendDate: String, moonPhases: Array<String>){
        val baseDate = "11/8/1999"
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val startDate = sdf.parse(baseDate)
        val endDate = sdf.parse(sendDate)
        val timeGone = kotlin.math.abs(endDate.time - startDate.time)
        val daysGone = timeGone / (24 * 60 * 60 * 1000)
        val moonAge = daysGone % 29.53058770576
        moonShape.text = moonAge.toString()
        if((0 < moonAge && moonAge <= 1) || (28.530588 < moonAge && moonAge <= 29.530588))
            moonShape.text = moonPhases[0]
        else if(1 < moonAge && moonAge <= 6.382647)
            moonShape.text = moonPhases[1]
        else if(6.382647 < moonAge && moonAge <= 8.382647)
            moonShape.text = moonPhases[2]
        else if(8.382647  < moonAge && moonAge <= 13.765294)
            moonShape.text = moonPhases[3]
        else if(13.765294 < moonAge && moonAge <= 15.765294)
            moonShape.text = moonPhases[4]
        else if(15.765294 < moonAge && moonAge <= 21.147941)
            moonShape.text = moonPhases[5]
        else if(21.147941 < moonAge && moonAge <= 23.147941)
            moonShape.text = moonPhases[6]
        else if(23.147941 < moonAge && moonAge <= 28.530588)
            moonShape.text = moonPhases[7]
    }

    private fun saveEventToFirestore(date: String, eventName: String) {

        if (events.text.isNotBlank()) {
            val dato = hashMapOf(
                "date" to date,
                "eventName" to eventName
            )
            eventsCollection.add(dato)
                .addOnSuccessListener { resultado ->
                    events.text= dato.toString()
                }
                .addOnFailureListener { exception ->

                }

        }

    }


}
