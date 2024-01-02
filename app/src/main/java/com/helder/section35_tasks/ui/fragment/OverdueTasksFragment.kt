package com.helder.section35_tasks.ui.fragment

class OverdueTasksFragment : BaseFragment() {

    override fun setToolbarTitle() {
        activity?.title = "Expired Tasks"
    }

    override fun getTasks() {
        viewModel.getOverdueTasks()
    }
}