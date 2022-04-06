package com.wyksofts.wyykweather.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.DialogFragment
import com.wyksofts.wyykweather.R

class CloseDialog : DialogFragment() {

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.close_dialog_bg)

        val view = inflater.inflate(R.layout.close_diag, container, false)

        val closeBtn = view.findViewById<View>(R.id.yes_exit)
        closeBtn.setOnClickListener {
            activity?.finish()
        }

        val undoBtn = view.findViewById<View>(R.id.undo)
        undoBtn.setOnClickListener {
            dismiss()
        }

        return view;
    }

}