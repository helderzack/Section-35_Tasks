package com.helder.section35_tasks.service.listener

import java.time.LocalDate

interface DateListener {

    fun onDateSet(selectedDate: LocalDate)
}