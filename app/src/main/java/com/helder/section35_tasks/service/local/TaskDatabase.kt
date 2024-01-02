package com.helder.section35_tasks.service.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.helder.section35_tasks.data.model.PriorityModel
import com.helder.section35_tasks.data.model.TaskModel
import com.helder.section35_tasks.service.constant.Constants

@Database(
    entities = [PriorityModel::class, TaskModel::class],
    version = Constants.Database.DATABASE_VERSION
)
abstract class TaskDatabase : RoomDatabase() {

    companion object {

        private lateinit var INSTANCE: TaskDatabase

        fun getDatabase(context: Context): TaskDatabase {
            if (!::INSTANCE.isInitialized) {
                synchronized(TaskDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        TaskDatabase::class.java,
                        Constants.Database.DATABASE_NAME
                    ).build()
                }
            }

            return INSTANCE
        }
    }

    abstract fun taskDao(): TaskDAO

    abstract fun priorityDao(): PriorityDAO
}