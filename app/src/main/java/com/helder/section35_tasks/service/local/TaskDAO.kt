package com.helder.section35_tasks.service.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.helder.section35_tasks.data.model.TaskModel
import com.helder.section35_tasks.service.constant.Constants

@Dao
interface TaskDAO {

    @Query("SELECT * FROM ${Constants.Database.TASK_TABLE}")
    fun getAll(): List<TaskModel>

    @Query("SELECT * FROM ${Constants.Database.TASK_TABLE} WHERE id = (:id)")
    fun findById(id: Int): TaskModel

    @Insert
    fun save(task: TaskModel): Long

    @Update
    fun update(task: TaskModel): Int

    @Delete
    fun delete(task: TaskModel): Int
}