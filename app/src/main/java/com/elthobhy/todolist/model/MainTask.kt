package com.elthobhy.todolist.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MainTask(
    val id : Int? = null,
    var title: String? = null,
    var details: String? = null,
    var date: String? = null,
    var isComplete: Boolean = false
): Parcelable
