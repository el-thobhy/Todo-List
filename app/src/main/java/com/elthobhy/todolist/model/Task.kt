package com.elthobhy.todolist.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val mainTask: MainTask? = null,
    val subTask: List<SubTask>? = null
): Parcelable