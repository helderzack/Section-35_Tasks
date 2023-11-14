package com.helder.section35_tasks.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.helder.section35_tasks.databinding.TasksFragmentsLayoutBinding
import com.helder.section35_tasks.ui.activities.AddTaskActivity

abstract class BaseFragment : Fragment() {

    private var _binding: TasksFragmentsLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TasksFragmentsLayoutBinding.inflate(inflater, container, false)

        binding.floatingButtonAddTask.setOnClickListener {
            startActivity(Intent(requireContext(), AddTaskActivity::class.java))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}