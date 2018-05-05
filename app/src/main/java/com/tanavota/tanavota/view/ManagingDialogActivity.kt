package com.tanavota.tanavota.view

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.tanavota.tanavota.view.common.SharedDialogFragment

open class ManagingDialogActivity : AppCompatActivity() {
    private var managedDialog: DialogFragment? = null
    var canShowDialogFragment = false

    override fun onResume() {
        super.onResume()

        canShowDialogFragment = true

        // Activityが破棄された時と、dismiss済みの場合は managedDialog に null が設定されるため表示されません。
        managedDialog?.let { showManagedDialog(it) }
    }

    override fun onPause() {
        super.onPause()

        // dialogが既に dismiss 済みの場合は onResume で再表示されないよに managedDialog に null を設定します。
        if (managedDialog?.dialog == null) {
            managedDialog = null
        } else {
            managedDialog?.dismiss()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        canShowDialogFragment = false
    }

    fun showManagedDialog(dialogBuilder: AlertDialog.Builder, cancelable: Boolean) {
        val dialogFragment = SharedDialogFragment.newInstance(cancelable)
        dialogFragment.dialogBuilder = dialogBuilder
        showManagedDialog(dialogFragment)
    }

    fun showManagedDialog(dialogFragment: DialogFragment) {
        // カレント画面がバックグラウンドから復帰し、onResume前にAlertDialogを表示する場合
        // onResume時に再度showManagedDialogが実行されるので、Fragmentが二重追加されないようにチェックする
        if (managedDialog != dialogFragment && canShowDialogFragment) {
            managedDialog = dialogFragment
            dialogFragment.show(this.supportFragmentManager, "dialog")
        }
    }
}