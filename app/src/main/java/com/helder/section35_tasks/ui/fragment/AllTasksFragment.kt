package com.helder.section35_tasks.ui.fragment

class AllTasksFragment : BaseFragment() {

    override fun setToolbarTitle() {
        activity?.title = "All Tasks"
    }

    override fun getTasks() {
        viewModel.getAllTasks()
    }
}