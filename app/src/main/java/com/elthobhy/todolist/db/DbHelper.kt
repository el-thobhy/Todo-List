package com.elthobhy.todolist.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.elthobhy.todolist.db.DbContract.MySubTask.Companion.SUB_TASK_ID
import com.elthobhy.todolist.db.DbContract.MySubTask.Companion.SUB_TASK_IS_COMPLETE
import com.elthobhy.todolist.db.DbContract.MySubTask.Companion.SUB_TASK_TASK_ID
import com.elthobhy.todolist.db.DbContract.MySubTask.Companion.SUB_TASK_TITLE
import com.elthobhy.todolist.db.DbContract.MySubTask.Companion.TABLE_SUB_TASK
import com.elthobhy.todolist.db.DbContract.MyTask.Companion.TABLE_TASK
import com.elthobhy.todolist.db.DbContract.MyTask.Companion.TASK_DATE
import com.elthobhy.todolist.db.DbContract.MyTask.Companion.TASK_DETAILS
import com.elthobhy.todolist.db.DbContract.MyTask.Companion.TASK_ID
import com.elthobhy.todolist.db.DbContract.MyTask.Companion.TASK_IS_COMPLETE
import com.elthobhy.todolist.db.DbContract.MyTask.Companion.TASK_TITLE

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "dbtodolist"
        const val DATABASE_VERSION = 1

        const val QUERY_CREATE_TASK = "CREATE TABLE $TABLE_TASK" +
                "($TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$TASK_TITLE TEXT NOT NULL," +
                "$TASK_DETAILS TEXT," +
                "$TASK_DATE TEXT," +
                "$TASK_IS_COMPLETE INTEGER NOT NULL)"

        const val QUERY_CREATE_SUB_TASK = "CREATE TABLE $TABLE_SUB_TASK" +
                "($SUB_TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$SUB_TASK_TASK_ID INTEGER NOT NULL," +
                "$SUB_TASK_TITLE TEXT," +
                "$SUB_TASK_IS_COMPLETE INTEGER NOT NULL," +
                "FOREIGN KEY ($SUB_TASK_TASK_ID) " +
                "REFERENCES $TABLE_TASK ($TASK_ID) " +
                "ON DELETE CASCADE " +
                "ON UPDATE CASCADE)"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(QUERY_CREATE_TASK)
        p0?.execSQL(QUERY_CREATE_SUB_TASK)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS TABLE_$TABLE_TASK")
        p0?.execSQL("DROP TABLE IF EXISTS TABLE_$TABLE_SUB_TASK")
        onCreate(p0)
    }
}