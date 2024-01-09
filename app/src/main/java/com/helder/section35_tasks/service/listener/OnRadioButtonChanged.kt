package com.helder.section35_tasks.service.listener

import com.helder.section35_tasks.data.model.TaskModel

interface OnRadioButtonChanged {

    fun onRadioButtonChanged(task: TaskModel)

}