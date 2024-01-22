package com.helder.section35_tasks.service.listener

interface OnImageViewClicked {

    fun onTaskMarkedComplete(id: Int)

    fun onTaskMarkedIncomplete(id: Int)

    fun onTaskCardClicked(id: Int)

    fun onLongClickToRemoval(id: Int)

}