package com.example.knowyourplants.ui.account

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.knowyourplants.R
import androidx.core.content.withStyledAttributes

class SquareView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val squarePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private var squareX = 0f
    private var squareY = 0f
    private var sideLength = 20f // default side length (in px)

    init {
        context.withStyledAttributes(attrs, R.styleable.SquareView) {

            val color = getColor(
                R.styleable.SquareView_squareColor,
                Color.BLUE
            )
            squarePaint.color = color

            sideLength = getDimension(
                R.styleable.SquareView_sideLength,
                20f
            )

        }
    }

    fun setSquarePosition(x: Float, y: Float) {
        squareX = x
        squareY = y
        invalidate()
    }

    fun setSquareColor(color: Int) {
        squarePaint.color = color
        invalidate()
    }

    fun setSideLength(size: Float) {
        sideLength = size
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val half = sideLength / 2
        canvas.drawRect(
            squareX - half,
            squareY - half,
            squareX + half,
            squareY + half,
            squarePaint
        )
    }
}
