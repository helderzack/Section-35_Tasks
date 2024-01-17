package com.helder.section35_tasks.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.helder.section35_tasks.data.model.PriorityModel
import com.helder.section35_tasks.data.model.TaskModel
import com.helder.section35_tasks.databinding.ActivityAddTaskBinding
import com.helder.section35_tasks.service.listener.DateListener
import com.helder.section35_tasks.ui.fragment.DatePickerFragment
import com.helder.section35_tasks.ui.viewmodel.AddTaskViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddTaskActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var viewModel: AddTaskViewModel

    private var dueDate = LocalDate.now()
    private lateinit var priorities: List<PriorityModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarAddTask)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this)[AddTaskViewModel::class.java]

        binding.textDatePickerSelector.setOnClickListener(this)
        binding.buttonAddTask.setOnClickListener(this)

        observe()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getPriorities()

                viewModel.priorities.collect {
                    priorities = it

                    ArrayAdapter(
                        applicationContext,
                        android.R.layout.simple_spinner_item,
                        priorities.map { priority -> priority.description }
                    )
                        .also { adapter ->
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            binding.spinnerTaskPriority.adapter = adapter
                        }
                }

            }
        }

        observe()
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
                createTask()
            }
        }
    }

    private fun openDatePicker() {
        val datePickerFragment = DatePickerFragment(dueDate, object : DateListener {
            override fun onDateSet(selectedDate: LocalDate) {
                dueDate = selectedDate
                val formattedDate = "${dueDate.dayOfMonth} ${dueDate.month} ${dueDate.year}"
                binding.textDatePickerSelector.text = formattedDate
            }

        })
        
        datePickerFragment.show(supportFragmentManager, "datePicker")
    }

    private fun createTask() {
        val priorityId = priorities[binding.spinnerTaskPriority.selectedItemPosition].id
        val isComplete = binding.checkboxComplete.isChecked
        val description = binding.editDescription.text.toString()

        val task = TaskModel(
            0,
            priorityId,
            description,
            dueDate,
            isComplete
        )

        Log.d("ADD_TASK", "$task")

        viewModel.createTask(task)
    }

    private fun observe() {
        viewModel.taskAddedSuccessfully.observe(this) {
            if (it.status()) {
                Toast.makeText(applicationContext, "Task added successfully!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(applicationContext, it.message(), Toast.LENGTH_SHORT).show()
            }
        }
    }

}