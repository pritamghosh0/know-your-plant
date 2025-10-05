package com.example.knowyourplants

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Window
import android.view.WindowInsets

object DimenUtils {

    /**
     * Returns screen dimensions in [height, width] format
     * */
    fun getScreenDimensions(activity: Activity?): Array<Int> {
        var screenHeight = 0
        var screenWidth = 0
        val displayMetrics = DisplayMetrics()
        val windowManager = activity?.windowManager
        if (windowManager != null) {
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.R) {
                val metrics = windowManager.getCurrentWindowMetrics().bounds
                screenHeight = metrics.height()
                screenWidth = metrics.width()
            } else {
                windowManager.defaultDisplay.getMetrics(displayMetrics)
                screenWidth = displayMetrics.widthPixels
                screenHeight = displayMetrics.heightPixels
            }
        }
        return arrayOf(screenHeight, screenWidth)
    }

    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }

    fun getStatusBarHeight(activity: Activity?): Int {
        var statusBarHeight = 0
        val rectangle = Rect()
        val window: Window? = activity?.window
        if(window!=null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val windowInsets: WindowInsets = window.decorView.getRootWindowInsets()
                statusBarHeight = windowInsets.getInsets(WindowInsets.Type.statusBars()).top
            } else {
                window.decorView.getWindowVisibleDisplayFrame(rectangle)
                statusBarHeight = rectangle.top
            }
        }
        return statusBarHeight
    }
}