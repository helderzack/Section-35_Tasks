package com.helder.section35_tasks.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.helder.section35_tasks.service.constant.Constants

@Entity(
    tableName = Constants.Database.TASK_TABLE,
    foreignKeys = [ForeignKey(
        entity = PriorityModel::class,
        childColumns = ["priorityId"],
        parentColumns = ["id"],
        onDelete = NO_ACTION
    )]
)
data class TaskModel(

    @SerializedName("Id")
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @SerializedName("PriorityId")
    @ColumnInfo(name = "priorityId")
    val priorityId: Int,

    @SerializedName("Description")
    @ColumnInfo(name = "description")
    val description: String,

    @SerializedName("DueDate")
    @ColumnInfo(name = "dueDate")
    val dueDate: String,

    @SerializedName("Complete")
    @ColumnInfo(name = "complete")
    val complete: Boolean
)