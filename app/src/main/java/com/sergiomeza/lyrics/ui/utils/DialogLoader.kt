package com.sergiomeza.lyrics.ui.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.sergiomeza.lyrics.R
import com.sergiomeza.lyrics.databinding.DialogLoaderBinding

object DialogLoader {
    fun create(context: Context): AlertDialog {

        val binding: DialogLoaderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_loader,null,
            false)

        val alertDialog: AlertDialog = binding.let {
            val builder = AlertDialog.Builder(context)
            builder.setView(binding.root)
            builder.setCancelable(false)
            builder.create()
        }

        val window: Window? = alertDialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.window?.attributes?.windowAnimations = R.style.PopDialogAnimation

        alertDialog.setOnShowListener {
            binding.LottieAnimationViewLoading.playAnimation()
        }

        alertDialog.setOnDismissListener {
            binding.LottieAnimationViewLoading.pauseAnimation()
        }
        return alertDialog
    }
}