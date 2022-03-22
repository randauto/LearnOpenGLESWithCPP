package com.learn.proandroidkotlin.customlayout

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup

class OwnCustomLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    companion object {
        const val TAG = "OwnCustomLayout"
    }

    override fun onLayout(p0: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childCount = childCount
        Log.d(TAG, "child count = $childCount")

        var left = l + paddingLeft
        var top = t + paddingTop
        var rowHeight = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            var childWidth = child.measuredWidth
            var childHeight = child.measuredHeight

            if (left + childWidth < r - paddingRight) {
                child.layout(left, top, left + childWidth, top + childHeight)
                left += childWidth
            } else {
                left = l + paddingLeft
                top += rowHeight
                rowHeight = 0
            }

            if (childHeight > rowHeight) {
                rowHeight = childHeight
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val count: Int = childCount
        var rowHeight = 0
        var maxWidth = 0
        var maxHeight = 0
        var left = 0
        var top = 0

        for (i in 0 until count) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            if (left + childWidth < width) {
                left += childWidth
            } else {
                if (left > maxWidth) {
                    maxWidth = left
                }
                left = 0
                top += rowHeight
                rowHeight = 0
            }

            if (childHeight > rowHeight) {
                rowHeight = childHeight
            }
        }

        if (left > maxWidth) {
            maxWidth = left
        }

        maxHeight = top + rowHeight

        setMeasuredDimension(
            getMeasure(widthMeasureSpec, maxWidth),
            getMeasure(heightMeasureSpec, maxHeight)
        )
    }

    private fun getMeasure(spec: Int, desired: Int) : Int {
        when (MeasureSpec.getMode(spec)) {
            MeasureSpec.EXACTLY -> {
                return MeasureSpec.getSize(spec)
            }

            MeasureSpec.AT_MOST -> {
                return Math.min(MeasureSpec.getSize(spec), desired)
            }

            MeasureSpec.UNSPECIFIED -> {
                return desired
            }
        }

        return desired
    }
}