package com.helder.section35_tasks.ui.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.helder.section35_tasks.service.listener.DateListener
import java.time.LocalDate

class DatePickerFragment(private val dueDate: LocalDate, private val listener: DateListener): DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = dueDate.year
        val month = dueDate.month
        val day = dueDate.dayOfMonth

        return DatePickerDialog(requireContext(), this, year, month.value - 1, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        val date = LocalDate.of(year, month + 1, day)
        listener.onDateSet(date)
    }

}