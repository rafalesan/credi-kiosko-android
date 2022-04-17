package com.rafalesan.credikiosko.presentation.base.utils

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rafalesan.credikiosko.presentation.R

object DialogHelper {

    data class DialogAttrs(var titleRes: String,
                           @DrawableRes var iconRes: Int,
                           var description: String?,
                           val onNegativeCallback: (() -> Unit)? = null,
                           val onPositiveCallback: (() -> Unit)? = null,
                           val onNeutralCallback: (() -> Unit)? = null,
                           val positiveButtonText: String? = null,
                           val negativeButtonText: String? = null,
                           val neutralButtonText: String? = null,
                           @ColorRes val iconTintColorRes: Int? = null)


    fun showDialog(context: Context, attr: DialogAttrs) {

        val builder = MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Center)

        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_custom_layout, null)

        val ivDialogIcon = dialogView.findViewById<AppCompatImageView>(R.id.ivDialogIcon)
        val tvDescription = dialogView.findViewById<AppCompatTextView>(R.id.tvDescription)

        with(attr) {
            attr.iconTintColorRes?.let {
                ivDialogIcon.setColorFilter(ContextCompat.getColor(context, it), android.graphics.PorterDuff.Mode.SRC_IN)
            }
            ivDialogIcon.setImageResource(iconRes)
            tvDescription.text = description
            builder.setTitle(titleRes)
        }

        builder.setView(dialogView)
        builder.setPositiveButton(attr.positiveButtonText ?: context.getString(R.string.accept)) { dialog, _ ->
            dialog.dismiss()
            attr.onPositiveCallback?.invoke()
        }
        attr.onNegativeCallback?.let {
            builder.setNegativeButton(attr.negativeButtonText ?: context.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
                it.invoke()
            }
        }

        attr.onNeutralCallback?.let {
            builder.setNeutralButton(attr.neutralButtonText ?: context.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
                it.invoke()
            }
        }

        val dialog = builder.create()

        dialog.show()

    }

    fun showNoInternetDialog(context: Context) {
        val noInternetDialogAttrs = DialogAttrs(context.getString(R.string.no_internet_connection),
                                                R.drawable.ic_no_internet,
                                                context.getString(R.string.no_internet_connection_description),
                                                iconTintColorRes = R.color.orange)

        showDialog(context, noInternetDialogAttrs)
    }

    fun showApiErrorDialog(context: Context, apiErrorMessage: String) {
        val apiErrorDialogAttrs = DialogAttrs(context.getString(R.string.message),
                                              R.drawable.ic_error,
                                              apiErrorMessage,
                                              iconTintColorRes = R.color.orange)
        showDialog(context, apiErrorDialogAttrs)
    }

    fun showApiNotAvailableErrorDialog(context: Context) {
        val apiNotAvailableDialogAttrs = DialogAttrs(context.getString(R.string.message),
                                                     R.drawable.ic_cloud_off,
                                                     context.getString(R.string.server_not_available_or_bad_internet_connection),
                                                     iconTintColorRes = R.color.orange)
        showDialog(context, apiNotAvailableDialogAttrs)
    }

    fun showUnknownErrorDialog(context: Context) {
        val unknownErrorDialogAttrs = DialogAttrs(context.getString(R.string.message),
                                              R.drawable.ic_device_unknown,
                                              context.getString(R.string.unknown_error_description),
                                              iconTintColorRes = R.color.orange)
        showDialog(context, unknownErrorDialogAttrs)
    }

    fun  buildProgressDialog(context: Context, description: String? = null): AlertDialog {

        val builder = MaterialAlertDialogBuilder(context)

        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_progress, null)

        val tvDescription = dialogView.findViewById<AppCompatTextView>(R.id.tvDescription)
        tvDescription.text = description ?: context.getString(R.string.loading)

        builder.setView(dialogView)

        return builder.create()

    }

}
