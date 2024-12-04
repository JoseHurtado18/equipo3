package com.example.miniproyecto1.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.miniproyecto1.R

class WaveView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var waveColor = ContextCompat.getColor(context, R.color.black)
    private var waveAmplitude = 100f
    private var waveWidth = 100f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = waveColor
        style = Paint.Style.FILL
    }

    private val path = Path()

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.WaveView)
        waveColor = attributes.getColor(R.styleable.WaveView_waveColor, waveColor)
        attributes.recycle()

        paint.color = waveColor
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        path.reset()
        path.moveTo(0f, height.toFloat())
        val waveWidth = width.toFloat() / 4f // Ancho de cada onda

        for (i in 0..3) {
            val startX = i * waveWidth
            val endX = startX + waveWidth
            val controlX = (startX + endX) / 2f

            val controlY = if (i % 2 == 0) waveAmplitude else -waveAmplitude

            path.quadTo(controlX, height - controlY, endX.toFloat(), height.toFloat())
        }

        path.lineTo(width.toFloat(), height.toFloat())
        path.close()

        canvas.drawPath(path, paint)
    }
}