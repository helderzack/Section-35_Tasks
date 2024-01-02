package com.helder.section35_tasks.service.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.helder.section35_tasks.data.model.PriorityModel
import com.helder.section35_tasks.service.constant.Constants

@Dao
interface PriorityDAO {

    @Query("SELECT * FROM ${Constants.Database.PRIORITY_TABLE}")
    fun getAll(): List<PriorityModel>

    @Insert
    fun save(priorities: List<PriorityModel>): LongArray

    @Query("DELETE FROM ${Constants.Database.PRIORITY_TABLE}")
    fun clear()
}