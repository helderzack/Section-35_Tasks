package com.helder.section35_tasks.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class TaskModel(

    @SerializedName("Id")
    val id: Int,

    @SerializedName("PriorityId")
    val priorityId: Int,

    @SerializedName("Description")
    val description: String,

    @SerializedName("DueDate")
    val dueDate: LocalDate,

    @SerializedName("Complete")
    val complete: Boolean
)