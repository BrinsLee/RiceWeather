package com.brins.riceweather.ui.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.brins.riceweather.R
import com.brins.riceweather.utils.DeviceUtils.dip2px

class LineProgressView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {

    companion object{
        val TAG = javaClass.simpleName
    }
    private val PROGRESS_STROKE_WIDTH = dip2px(2f)
    private val mProgressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mProgressBgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mProgressBgRect: RectF? = null
    private var mProgressRect: RectF? = null
    val clampWidth = PROGRESS_STROKE_WIDTH / 2
    var mPro = 10f
        set(value) {
            field = value
            drawLine()
        }
    var mProgress: Float = 10f

    private fun drawLine() {
        val valueAnimator = ValueAnimator.ofFloat(mPro)
        valueAnimator.duration = 1500
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.addUpdateListener {
            mProgress = it.animatedValue as Float
            mProgressRect = RectF(
                clampWidth.toFloat(),
                clampWidth.toFloat(),
                mProgress,
                dip2px(5f).toFloat()
            )
            postInvalidate()
        }
        valueAnimator.start()
        mProgress = mPro
        mProgressRect = RectF(
            clampWidth.toFloat(),
            clampWidth.toFloat(),
            mProgress,
            dip2px(5f).toFloat()
        )
        postInvalidate()
    }

    @ColorRes
    private var startColor = R.color.defaultStartColor
    @ColorRes
    private var endColor = R.color.defaultEndColor

    init {

        val a = context.obtainStyledAttributes(attributeSet, R.styleable.LineProgressView)
        startColor = a.getColor(
            R.styleable.LineProgressView_start_color,
            ContextCompat.getColor(context, R.color.defaultStartColor)
        )
        endColor = a.getColor(
            R.styleable.LineProgressView_start_color,
            ContextCompat.getColor(context, R.color.defaultEndColor)
        )
        a.recycle()
        mProgressPaint.strokeCap = Paint.Cap.ROUND
        mProgressPaint.strokeWidth = PROGRESS_STROKE_WIDTH.toFloat()
        mProgressPaint.style = Paint.Style.FILL

        mProgressBgPaint.strokeCap = Paint.Cap.ROUND
        mProgressBgPaint.strokeWidth = PROGRESS_STROKE_WIDTH.toFloat()
        mProgressBgPaint.color = Color.WHITE
        mProgressBgPaint.style = Paint.Style.FILL

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
        calculateSize()
    }

    private fun calculateSize() {
        mProgressPaint.shader = LinearGradient(
            0f, 0f, width.toFloat(), 0f,
            startColor, endColor, Shader.TileMode.CLAMP
        )

        mProgressBgRect = RectF(
            clampWidth.toFloat(), clampWidth.toFloat(), (measuredWidth - clampWidth).toFloat(),
            dip2px(5f).toFloat()
        )
        mProgressRect = RectF(
            clampWidth.toFloat(),
            clampWidth.toFloat(),
            mProgress,
            dip2px(5f).toFloat()
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRoundRect(mProgressBgRect!!, 12f,
            12f, mProgressBgPaint)

        canvas.drawRoundRect(mProgressRect!!, 12f, 12f, mProgressPaint)
    }
}