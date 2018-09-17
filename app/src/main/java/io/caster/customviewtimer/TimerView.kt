package io.caster.customviewtimer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

class TimerView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var backgroundPaint: Paint = Paint()
    private var numberPaint: TextPaint = TextPaint()
    private var startTime = 0L

    private val updateRunnable = Runnable { updateTimer() }

    init {
        backgroundPaint.color = Color.parseColor("#880E4F")
        numberPaint.color = ContextCompat.getColor(context, android.R.color.white)
        numberPaint.textSize = 64f * resources.displayMetrics.density
    }

    fun start() {
        startTime = System.currentTimeMillis()
        updateTimer()
    }

    fun stop() {
        startTime = 0
        removeCallbacks(updateRunnable)
    }

    override fun onDraw(canvas: Canvas) {
        val canvasWidth = width
        val canvasHeight = height

        val centerX = Math.round(canvasWidth * 0.5f).toFloat()
        val centerY = Math.round(canvasHeight * 0.5f).toFloat()

        val radius = (if (canvasWidth < canvasHeight) canvasWidth else canvasHeight) * 0.5f

        val number = ((System.currentTimeMillis() - startTime) * 0.001).toString()

        val textOffSetX = numberPaint.measureText(number) * 0.5f
        val textOffSetY = numberPaint.fontMetrics.ascent * -0.4f

        canvas.drawCircle(centerX, centerY, radius, backgroundPaint)
        canvas.drawText(number, centerX - textOffSetX, centerY + textOffSetY, numberPaint)
    }

    private fun updateTimer(){
        invalidate()
        postDelayed(updateRunnable, 200L)
    }
}