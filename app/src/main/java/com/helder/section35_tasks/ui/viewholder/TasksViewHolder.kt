package com.helder.section35_tasks.ui.viewholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.helder.section35_tasks.data.model.TaskModel
import com.helder.section35_tasks.databinding.TaskItemBinding
import com.helder.section35_tasks.service.listener.OnRadioButtonChanged

class TasksViewHolder(
    private val binding: TaskItemBinding,
    private val listener: OnRadioButtonChanged
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: TaskModel, priority: String) {
        Log.d("TASKS_FETCHING", task.toString())
        binding.textTaskDescription.text = task.description
        binding.textTaskPriority.text = priority
        binding.textTaskLimitDate.text = task.dueDate.toString()

        binding.radioCompletedTask.setOnCheckedChangeListener { buttonView, isChecked ->
//            if(isChecked) {
//                buttonView.setButtonIcon()
//            }
            task.complete = isChecked
            listener.onRadioButtonChanged(task)
        }

        if (task.complete) {
            binding.radioCompletedTask.isChecked = true
        }
    }

}