package com.example.dobcalc

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.ButtonDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)

        btnDatePicker.setOnClickListener {
            clickDatePicker()

        }

    }

     @RequiresApi(Build.VERSION_CODES.N)
     private fun clickDatePicker(){
         val myCalendar = Calendar.getInstance()
         val year = myCalendar.get(Calendar.YEAR)
         val month = myCalendar.get(Calendar.MONTH)
         val day = myCalendar.get(Calendar.DAY_OF_MONTH)
         val dpd = DatePickerDialog(this,
            {_, selectedYear, selectedMonth, selectedDayOfMonth ->
                //Toast.makeText(this, "THE DATE WAS $selectedDay / ${selectedDayOfMonth+1} / $selectedYear  ", Toast.LENGTH_LONG).show()
                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"

                tvSelectedDate?.text = selectedDate
                //set date format
                val sdf = SimpleDateFormat("dd/MM/yy", Locale.ENGLISH)
                //set the selected date
                val theDate = sdf.parse(selectedDate)

                theDate?.let{
                    //convert selected date in mins
                    val selectedDateInMinutes = theDate.time / 60000
                    //set current date
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    //convert current date in mins
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time/60000
                        //calc difference
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                    }

                }


            },
            year,
            month,
            day)
         dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
         dpd.show()

    }
}