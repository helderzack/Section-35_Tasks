package com.helder.section35_tasks.ui.fragment

class NextSevenDaysTasksFragment: BaseFragment() {

    override fun setToolbarTitle() {
        activity?.title = "Next 7 Days"
    }

    override fun getTasks() {
        viewModel.getNextSevenDaysTasks()
    }
}