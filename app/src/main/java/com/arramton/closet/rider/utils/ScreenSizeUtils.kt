package com.arramton.cakingom.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

class ScreenSizeUtils {
    companion object {
        fun getScreenWidth(context: Context): Int {
            val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getMetrics(dm)
            return dm.widthPixels
        }
    }
}