package com.helder.section35_tasks.ui.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.helder.section35_tasks.service.listener.OnDialogOptions

class DeleteGameDialogFragment(private val listener: OnDialogOptions) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setMessage("Do you wan to delete the Task?")
                .setPositiveButton("Yes") { _, _ ->
                    listener.onDeleteClick()
                }.setNeutralButton("Cancel", null)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}