package com.helder.section35_tasks.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.helder.section35_tasks.data.model.TaskModel
import java.text.SimpleDateFormat
import java.util.Calendar

class TasksViewModel(application: Application) : AndroidViewModel(application) {
    fun getData(): List<TaskModel> {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val defaultDate = simpleDateFormat.format(calendar.time)

        return listOf(
            TaskModel("Programming", "Low", defaultDate),
            TaskModel("Cooking", "High", defaultDate),
            TaskModel("Brushing", "Critical", defaultDate),
            TaskModel("Plant a tree", "Medium", defaultDate),
        )
    }
}