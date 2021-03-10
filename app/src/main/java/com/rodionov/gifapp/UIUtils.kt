package com.rodionov.gifapp

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.view.WindowManager

/**
 * Created by rodionov on 23.11.2019.
 */
class UIUtils {

    companion object {

        fun getScreenWidthInPx(context: Context): Int {
            val display =
                (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            val size = Point()
            display.getSize(size)
            return size.x
        }

        fun convertDpToPixel(dp: Float): Float {
            val resources = Resources.getSystem()
            val metrics = resources.displayMetrics
            return dp * metrics.density
        }
    }
}