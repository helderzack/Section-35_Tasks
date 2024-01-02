package com.helder.section35_tasks.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.helder.section35_tasks.data.model.TaskModel
import com.helder.section35_tasks.databinding.TaskItemBinding

class TasksViewHolder(private val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(task: TaskModel) {
        binding.textTaskDescription.text = task.description
        binding.textTaskPriority.text = "High"
        binding.textTaskLimitDate.text = task.dueDate
    }
}