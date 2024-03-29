package com.helder.section35_tasks.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.helder.section35_tasks.data.model.PriorityModel
import com.helder.section35_tasks.databinding.TasksFragmentsLayoutBinding
import com.helder.section35_tasks.service.constant.Constants
import com.helder.section35_tasks.service.listener.OnDialogOptions
import com.helder.section35_tasks.service.listener.TaskListener
import com.helder.section35_tasks.ui.activity.TaskFormActivity
import com.helder.section35_tasks.ui.adapter.TasksAdapter
import com.helder.section35_tasks.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

abstract class BaseFragment : Fragment() {

    private var _binding: TasksFragmentsLayoutBinding? = null
    private val binding get() = _binding!!
    protected lateinit var viewModel: BaseViewModel
    private lateinit var adapter: TasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TasksFragmentsLayoutBinding.inflate(inflater, container, false)

        binding.floatingButtonAddTask.setOnClickListener {
            startActivity(Intent(requireContext(), TaskFormActivity::class.java))
        }

        viewModel = ViewModelProvider(this)[BaseViewModel::class.java]

        adapter = TasksAdapter(object : TaskListener {
            override fun onTaskMarkedComplete(id: Int) {
                viewModel.markComplete(id)
            }

            override fun onTaskMarkedIncomplete(id: Int) {
                viewModel.markIncomplete(id)
            }

            override fun onTaskCardClick(id: Int) {
                val intent = Intent(requireActivity(), TaskFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(Constants.BUNDLE.TASK_ID, id)
                intent.putExtras(bundle)
                requireActivity().startActivity(intent)
            }

            override fun onLongClickToRemoval(id: Int) {
                DeleteGameDialogFragment(object : OnDialogOptions {
                    override fun onDeleteClick() {
                        viewModel.deleteTask(id)
                    }
                }).show(requireActivity().supportFragmentManager, "DELETE_DIALOG")
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
                        binding.recyclerViewTasks.layoutManager =
                            LinearLayoutManager(requireContext())
                        binding.recyclerViewTasks.adapter = adapter
                    }
                }
            }
        }

        setToolbarTitle()

        observe()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tasks.collect {
                    adapter.updateTasks(it)
                }
            }
        }

        viewModel.wasTaskUpdated.observe(viewLifecycleOwner) {
            if (it.status()) {
                Toast.makeText(requireContext(), "Task updated!", Toast.LENGTH_SHORT).show()
                getTasks()
            } else {
                Toast.makeText(requireContext(), it.message(), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.wasTaskDeleted.observe(viewLifecycleOwner) {
            if (it.status()) {
                Toast.makeText(requireContext(), "Task deleted!", Toast.LENGTH_SHORT).show()
                getTasks()
            } else {
                Toast.makeText(requireContext(), it.message(), Toast.LENGTH_SHORT).show()
            }
        }

    }

    abstract fun setToolbarTitle()

    abstract fun getTasks()
}