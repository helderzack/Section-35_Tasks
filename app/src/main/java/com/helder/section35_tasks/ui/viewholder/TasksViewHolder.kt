package com.helder.section35_tasks.ui.viewholder

import android.view.View
import android.view.View.OnClickListener
import androidx.recyclerview.widget.RecyclerView
import com.helder.section35_tasks.R
import com.helder.section35_tasks.data.model.TaskModel
import com.helder.section35_tasks.databinding.TaskItemBinding
import com.helder.section35_tasks.service.listener.OnImageViewClicked

class TasksViewHolder(private val binding: TaskItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(task: TaskModel, priority: String, listener: OnImageViewClicked) {
        binding.textTaskDescription.text = task.description
        binding.textTaskPriority.text = priority
        binding.textTaskLimitDate.text = task.dueDate.toString()

        if(task.complete) {
            binding.imageViewCompletedTask.setImageResource(R.drawable.ic_checked)
        } else {
            binding.imageViewCompletedTask.setImageResource(R.drawable.ic_unchecked)
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