package com.helder.section35_tasks.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.helder.section35_tasks.data.model.PriorityModel
import com.helder.section35_tasks.data.model.TaskModel
import com.helder.section35_tasks.databinding.TasksFragmentsLayoutBinding
import com.helder.section35_tasks.service.listener.OnRadioButtonChanged
import com.helder.section35_tasks.ui.activity.AddTaskActivity
import com.helder.section35_tasks.ui.adapter.TasksAdapter
import com.helder.section35_tasks.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

abstract class BaseFragment : Fragment() {

    private var _binding: TasksFragmentsLayoutBinding? = null
    private val binding get() = _binding!!
    protected lateinit var viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TasksFragmentsLayoutBinding.inflate(inflater, container, false)

        binding.floatingButtonAddTask.setOnClickListener {
            startActivity(Intent(requireContext(), AddTaskActivity::class.java))
        }

        viewModel = ViewModelProvider(this)[BaseViewModel::class.java]

        val adapter = TasksAdapter(object : OnRadioButtonChanged {
            override fun onRadioButtonChanged(task: TaskModel) {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.updateTask(task)
                    getTasks()
                }
            }

        })

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                var priorities: List<PriorityModel> = mutableListOf()
                launch {
                    viewModel.getPriorities()
                    viewModel.receivedPriorities.collect {
                        priorities = it
                    }
                }
                launch {
                    getTasks()
                    viewModel.tasks.collect {
                        Log.d("TASKS_FETCHING", "Calling View Model")
                        adapter.setData(it, priorities)
                        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(requireContext())
                        binding.recyclerViewTasks.adapter = adapter
                    }
                }
            }
        }

        setToolbarTitle()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun setToolbarTitle()

    abstract fun getTasks()
}