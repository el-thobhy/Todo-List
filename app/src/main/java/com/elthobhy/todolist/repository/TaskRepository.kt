package com.elthobhy.todolist.repository

import android.content.Context
import com.elthobhy.todolist.model.Tasks
import com.google.gson.Gson
import java.io.IOException

object TaskRepository {

    fun getDataTask(context: Context?): Tasks?{
        val json: String?
        try{
            val inputStream = context?.assets?.open("tasks.json")
            json = inputStream?.bufferedReader().use { it?.readText() }
        }catch (e: IOException){
            e.printStackTrace()
            return null
        }
        return Gson().fromJson(json, Tasks::class.java)
    }
}