package com.elthobhy.todolist.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainTask(
    val id : Int? = null,
    val title: String? = null,
    val details: String? = null,
    val date: String? = null,
    var isComplete: Boolean = false
): Parcelable
