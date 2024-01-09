package com.helder.section35_tasks.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class TaskModel(

    @SerializedName("Id")
    val id: Int,

    @SerializedName("PriorityId")
    var priorityId: Int,

    @SerializedName("Description")
    var description: String,

    @SerializedName("DueDate")
    var dueDate: LocalDate,

    @SerializedName("Complete")
    var complete: Boolean
)