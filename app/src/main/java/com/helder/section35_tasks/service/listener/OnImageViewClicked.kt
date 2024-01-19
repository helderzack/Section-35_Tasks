package com.helder.section35_tasks.service.listener

import com.helder.section35_tasks.data.model.TaskModel

interface OnImageViewClicked {

    fun onTaskMarkedComplete(id: Int)

    fun onTaskMarkedIncomplete(id: Int)

    fun onTaskCardClicked(id: Int)

}