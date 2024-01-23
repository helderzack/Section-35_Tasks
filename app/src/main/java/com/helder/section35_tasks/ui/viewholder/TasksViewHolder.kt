package com.helder.section35_tasks.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.helder.section35_tasks.R
import com.helder.section35_tasks.data.model.TaskModel
import com.helder.section35_tasks.databinding.TaskItemBinding
import com.helder.section35_tasks.service.listener.OnImageViewClicked
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TasksViewHolder(private val binding: TaskItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(task: TaskModel, priority: String, listener: OnImageViewClicked) {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date = LocalDate.parse(task.dueDate.toString())
        val dateString = date.format(dateTimeFormatter)

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
                task.complete = false
                listener.onTaskMarkedIncomplete(task.id)
                binding.imageViewCompletedTask.setImageResource(R.drawable.ic_unchecked)
            } else {
                task.complete = true
                listener.onTaskMarkedComplete(task.id)
                binding.imageViewCompletedTask.setImageResource(R.drawable.ic_checked)
            }
        }

        binding.cardViewTask.setOnClickListener {
            listener.onTaskCardClicked(task.id)
        }
    }

}