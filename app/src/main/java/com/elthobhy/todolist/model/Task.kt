package com.elthobhy.todolist.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    var mainTask: MainTask? = null,
    var subTask: List<SubTask>? = null
): Parcelable