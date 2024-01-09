package com.helder.section35_tasks.ui.viewholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.helder.section35_tasks.data.model.TaskModel
import com.helder.section35_tasks.databinding.TaskItemBinding

class TasksViewHolder(private val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: TaskModel, priority: String) {
        Log.d("TASKS_FETCHING", task.toString())
        binding.textTaskDescription.text = task.description
        binding.textTaskPriority.text = priority
        binding.textTaskLimitDate.text = task.dueDate.toString()
    }

}