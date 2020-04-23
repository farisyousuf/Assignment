package com.example.enbdassignment.util

import android.app.AlertDialog
import android.content.Context
import com.example.enbdassignment.R

object DialogFactory {

    /**
     * Show error messages
     */
    fun showErrorDialog(
        context: Context,
        message: String?,
        errorTitle: String = context.getString(R.string.error_title)
    ) {
        val alertDialog = AlertDialog.Builder(context)
            .setTitle(errorTitle)
            .setPositiveButton(context.getString(R.string.ok), null)
            .setMessage(message)
            .create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}