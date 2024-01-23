package com.helder.section35_tasks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.helder.section35_tasks.data.model.PriorityModel
import com.helder.section35_tasks.data.model.TaskModel
import com.helder.section35_tasks.databinding.TaskItemBinding
import com.helder.section35_tasks.service.listener.TaskListener
import com.helder.section35_tasks.ui.viewholder.TasksViewHolder

class TasksAdapter(private val listener: TaskListener) :
    RecyclerView.Adapter<TasksViewHolder>() {

    private lateinit var binding: TaskItemBinding
    private var tasks: List<TaskModel> = mutableListOf()
    private var priorities: List<PriorityModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TasksViewHolder(binding)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.bind(
            tasks[position],
            priorities.first { priority -> priority.id == tasks[position].priorityId }.description,
            listener
        )
    }

    fun setData(tasks: List<TaskModel>, priorities: List<PriorityModel>) {
        this.tasks = tasks
        this.priorities = priorities
    }

    fun updateTasks(tasks: List<TaskModel>) {
        this.tasks = tasks
    }

}