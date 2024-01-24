package com.helder.section35_tasks.service.dateformatter

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PreferredDateFormatter private constructor() {

    companion object {

        private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        fun formatDate(dateString: String): String {
            val date = LocalDate.parse(dateString)
            return date.format(dateFormatter)
        }

    }

}