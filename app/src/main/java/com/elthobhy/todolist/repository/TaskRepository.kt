package com.elthobhy.todolist.repository

import android.content.Context
import com.elthobhy.todolist.db.DbSubTaskHelper
import com.elthobhy.todolist.db.DbTaskHelper
import com.elthobhy.todolist.model.MainTask
import com.elthobhy.todolist.model.Task
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
    fun getDataTaskFromDatabase(dbTaskHelper: DbTaskHelper, dbSubTaskHelper: DbSubTaskHelper): List<Task>?{
        val tasks = mutableListOf<Task>()
        val mainTask = dbTaskHelper.getAllTask()
        tasks.clear()

        if(mainTask != null){
            for(mainTask: MainTask in mainTask){
                val task = Task()
                task.mainTask = mainTask

                val subTask = dbSubTaskHelper.getAllSubTask(mainTask.id)
                if(subTask != null && subTask.isNotEmpty()){
                    task.subTask = subTask
                }
                tasks.add(task)
            }
        }else{
            return null
        }
        return tasks
    }

    fun getDataTaskCompleteFromDatabase(dbTaskHelper: DbTaskHelper, dbSubTaskHelper: DbSubTaskHelper): List<Task>?{
        val tasks = mutableListOf<Task>()
        val mainTask = dbTaskHelper.getAllTaskComplete()
        tasks.clear()

        if(mainTask != null){
            for(mainTask: MainTask in mainTask){
                val task = Task()
                task.mainTask = mainTask

                val subTask = dbSubTaskHelper.getAllSubTask(mainTask.id)
                if(subTask != null && subTask.isNotEmpty()){
                    task.subTask = subTask
                }
                tasks.add(task)
            }
        }else{
            return null
        }
        return tasks
    }
}