package com.wyksofts.wyykweather.utils

import android.content.Context
import android.widget.Toast
import es.dmoral.toasty.Toasty

class showToast {

    //show success toast
    fun showSuccess(context: Context, message: String) {
        Toasty.success(
            context, message,
            Toast.LENGTH_SHORT,
            true
        ).show()
    }


    //show warning failure toast
    fun showFailure(context: Context, message: String) {
        Toasty.error(
            context, message,
            Toast.LENGTH_SHORT, true
        ).show()
    }
}