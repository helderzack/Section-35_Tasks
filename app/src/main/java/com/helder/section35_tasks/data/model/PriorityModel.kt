package com.helder.section35_tasks.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.helder.section35_tasks.service.constant.Constants

@Entity(tableName = Constants.Database.PRIORITY_TABLE)
data class PriorityModel(

    @SerializedName("Id")
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @SerializedName("Description")
    @ColumnInfo(name = "description")
    val description: String
)
