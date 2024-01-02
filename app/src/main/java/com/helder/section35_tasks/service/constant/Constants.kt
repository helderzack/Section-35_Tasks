package com.helder.section35_tasks.service.constant

class Constants {
    object RequestHeaders {
        const val TOKEN = "token"
        const val PERSON_KEY = "personKey"
        const val NAME = "name"
    }

    object HTTPStatusCode {
        const val OK = 200
    }

    object Database {
        const val DATABASE_NAME = "Task.db"
        const val DATABASE_VERSION = 1
        const val TASK_TABLE = "task"
        const val PRIORITY_TABLE = "priority"
    }
}
