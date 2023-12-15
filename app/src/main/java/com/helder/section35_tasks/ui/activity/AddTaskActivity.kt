package com.helder.section35_tasks.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.helder.section35_tasks.R
import com.helder.section35_tasks.databinding.ActivityAddTaskBinding
import com.helder.section35_tasks.ui.fragment.DatePickerFragment

class AddTaskActivity : AppCompatActivity(), OnClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityAddTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarAddTask)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.textDatePickerSelector.setOnClickListener(this)
        binding.buttonAddTask.setOnClickListener(this)
        binding.spinnerTaskPriority.onItemSelectedListener = this

        ArrayAdapter
            .createFromResource(
                this,
                R.array.priorities_array,
                android.R.layout.simple_spinner_item
            )
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerTaskPriority.adapter = adapter
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onClick(item: View?) {
        when (item) {
            binding.textDatePickerSelector -> {
                openDatePicker()
            }

            binding.buttonAddTask -> {

            }
        }
    }

    private fun openDatePicker() {
        Log.d("APP_BEHAVIOR", "DATE_PICKER_CLICKED")
        val datePickerFragment = DatePickerFragment()
        datePickerFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        val taskPriority = parent.getItemAtPosition(position)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}