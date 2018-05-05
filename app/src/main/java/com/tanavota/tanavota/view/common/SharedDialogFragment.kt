package com.tanavota.tanavota.view.common

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog


/**
 */
class SharedDialogFragment() : DialogFragment() {
    var dialogBuilder: AlertDialog.Builder? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = dialogBuilder?.create() ?: Dialog(this.context)
        return dialog
    }

    companion object {
        fun newInstance(cancelable: Boolean = false): SharedDialogFragment {
            return SharedDialogFragment().apply {
                isCancelable = cancelable
            }
        }
    }
}

