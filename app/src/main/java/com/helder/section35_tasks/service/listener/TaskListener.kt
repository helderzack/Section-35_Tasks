package com.helder.section35_tasks.service.listener

interface TaskListener {

    fun onListClick(id: Int)

    fun onDeleteClick(id: Int)

    fun onCompleteClick(id: Int)

    fun onUndoClick(id: Int)

}