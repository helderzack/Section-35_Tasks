package com.helder.section35_tasks.service.listener

interface TaskListener {

    fun onTaskMarkedComplete(id: Int)

    fun onTaskMarkedIncomplete(id: Int)

    fun onTaskCardClick(id: Int)

    fun onLongClickToRemoval(id: Int)

}