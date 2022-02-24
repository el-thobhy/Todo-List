package com.elthobhy.todolist.utils

import android.app.DatePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

object DatePickerSet {
    fun showDatePicker(context: Context, onDateSetListener: DatePickerDialog.OnDateSetListener) {
        //get current Date
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(context, onDateSetListener, year, month, day).show()
    }

    //
    fun dateFormatSql(year: Int, month: Int, dayOfMonth: Int): String{
        return "$year-$month-$dayOfMonth"
    }

    //format date from SQL to View
    fun dateFromSqlToDateViewTask(rawDate: String): String{
        var result = ""
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(rawDate)
        if(date != null){
            result = SimpleDateFormat("EEE, dd MM yyyy", Locale.getDefault()).format(date)
        }
        return result
    }

}