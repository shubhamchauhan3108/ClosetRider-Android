package com.arramton.cakingom.utils

import android.os.SystemClock
import android.view.View
import java.util.WeakHashMap

abstract class DebouncedOnClickListener(private val minimumIntervalMillis: Long) : View.OnClickListener {

    private val lastClickMap: MutableMap<View, Long> = WeakHashMap()

    /**
     * Implement this in your subclass instead of onClick
     * @param v The view that was clicked
     */
    abstract fun onDebouncedClick(v: View)

    override fun onClick(clickedView: View) {
        val previousClickTimestamp = lastClickMap[clickedView]
        val currentTimestamp = SystemClock.uptimeMillis()

        lastClickMap[clickedView] = currentTimestamp
        if (previousClickTimestamp == null || Math.abs(currentTimestamp - previousClickTimestamp) > minimumIntervalMillis) {
            onDebouncedClick(clickedView)
        }
    }
}