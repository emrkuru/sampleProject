package com.emrekuru.example.base

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.emrekuru.example.R

open class BaseActivity: AppCompatActivity() {

    private var dialogLoading: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLoadingDialog()

    }
    fun showLoading() {
        hideLoading()
        dialogLoading?.show()
    }

    fun hideLoading() {
        dialogLoading?.dismiss()
    }

    private fun initLoadingDialog(){
        dialogLoading = Dialog(this)
        dialogLoading?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            setCancelable(false)
            setContentView(R.layout.dialog_loading)
        }
    }
}