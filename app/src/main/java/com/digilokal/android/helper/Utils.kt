package com.digilokal.android.helper

import android.os.Build
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun setupFullScreenMode(window: Window?, actionBar: ActionBar?) {
    @Suppress("DEPRECATION")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window?.insetsController?.hide(WindowInsets.Type.statusBars())
    } else {
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
    actionBar?.hide()
}

fun formatFollowersCount(count: Long): String {
    return formatFollowersCount(count.toInt())
}

fun formatFollowersCount(count: String): String {
    return formatFollowersCount(count.toInt())
}

fun formatFollowersCount(count: Int): String {
    return when {
        count >= 1000 -> {
            val roundedCount = (count / 100).toFloat() / 10
            "${roundedCount}K"
        }
        else -> count.toString()
    }
}

fun formatPrice(count: String): String {
    return formatPrice(count.toInt())
}
fun formatPrice(price: Int): String {
    val symbols = DecimalFormatSymbols()
    symbols.groupingSeparator = '.'
    val decimalFormat = DecimalFormat("#,###", symbols)
    return decimalFormat.format(price)
}