package com.helder.section35_tasks.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.helder.section35_tasks.R
import com.helder.section35_tasks.data.model.TaskModel
import com.helder.section35_tasks.databinding.TaskItemBinding
import com.helder.section35_tasks.service.dateformatter.PreferredDateFormatter
import com.helder.section35_tasks.service.listener.TaskListener

class TasksViewHolder(private val binding: TaskItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(task: TaskModel, priority: String, listener: TaskListener) {
        val dateString = PreferredDateFormatter.formatDate(task.dueDate.toString())

        binding.textTaskDescription.text = task.description
        binding.textTaskPriority.text = priority
        binding.textTaskLimitDate.text = dateString

        if(task.complete) {
            binding.imageViewCompletedTask.setImageResource(R.drawable.ic_checked)
        } else {
            binding.imageViewCompletedTask.setImageResource(R.drawable.ic_unchecked)
        }

        binding.cardViewTask.setOnLongClickListener {
            listener.onLongClickToRemoval(task.id)
            true
        }

        binding.imageViewCompletedTask.setOnClickListener {
            if(task.complete) {
                listener.onTaskMarkedIncomplete(task.id)
                binding.imageViewCompletedTask.setImageResource(R.drawable.ic_unchecked)
            } else {
                listener.onTaskMarkedComplete(task.id)
                binding.imageViewCompletedTask.setImageResource(R.drawable.ic_checked)
            }
        }

        binding.cardViewTask.setOnClickListener {
            listener.onTaskCardClick(task.id)
        }
    }

}