package com.helder.section35_tasks.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.helder.section35_tasks.data.model.TaskModel
import com.helder.section35_tasks.databinding.TaskItemBinding

class TasksViewHolder(private val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(task: TaskModel) {
        binding.textTaskDescription.text = task.description
        binding.textTaskPriority.text = task.priority
        binding.textTaskLimitDate.text = task.date.toString()
    }
}