package com.example.knowyourplants.ui.account

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.knowyourplants.R
import androidx.core.content.withStyledAttributes

class DotView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL // Fill the dot
        isAntiAlias = true      // Smooth edges
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.DotView) {
            val color = getColor(
                R.styleable.DotView_dotColor,
                Color.GRAY // default color
            )
            dotPaint.color = color
        }
    }

    private var dotX = 0f
    private var dotY = 0f
    private var radius = 10f

    fun setDotPosition(x: Float, y: Float, r: Float) {
        dotX = x
        dotY = y
        radius = r
        invalidate() // request a redraw of the view
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(dotX, dotY, radius, dotPaint)
    }
}
