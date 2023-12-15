package com.helder.section35_tasks.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.helder.section35_tasks.databinding.TasksFragmentsLayoutBinding
import com.helder.section35_tasks.ui.activity.AddTaskActivity
import com.helder.section35_tasks.ui.adapter.TasksAdapter
import com.helder.section35_tasks.ui.viewmodel.TasksViewModel

abstract class BaseFragment : Fragment() {

    private var _binding: TasksFragmentsLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TasksFragmentsLayoutBinding.inflate(inflater, container, false)

        binding.floatingButtonAddTask.setOnClickListener {
            startActivity(Intent(requireContext(), AddTaskActivity::class.java))
        }

        viewModel = ViewModelProvider(this)[TasksViewModel::class.java]

        val adapter  = TasksAdapter()
        adapter.setData(viewModel.getData())

        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTasks.adapter = adapter

        setToolbarTitle()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun setToolbarTitle()
}