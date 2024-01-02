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
import com.helder.section35_tasks.databinding.TasksFragmentsLayoutBinding
import com.helder.section35_tasks.ui.activity.AddTaskActivity
import com.helder.section35_tasks.ui.adapter.TasksAdapter
import com.helder.section35_tasks.ui.viewmodel.TasksViewModel
import kotlinx.coroutines.launch

abstract class BaseFragment : Fragment() {

    private var _binding: TasksFragmentsLayoutBinding? = null
    private val binding get() = _binding!!
    protected lateinit var viewModel: TasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("WHAT_THE_FUCK", "Fragment, nigga")
        _binding = TasksFragmentsLayoutBinding.inflate(inflater, container, false)

        binding.floatingButtonAddTask.setOnClickListener {
            startActivity(Intent(requireContext(), AddTaskActivity::class.java))
        }

        viewModel = ViewModelProvider(this)[TasksViewModel::class.java]

        val adapter  = TasksAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                getTasks()
                viewModel.tasks.collect {
                    adapter.setData(it)
                }
            }
        }

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

    abstract fun getTasks()
}