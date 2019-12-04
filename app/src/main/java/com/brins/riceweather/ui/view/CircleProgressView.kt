package com.brins.riceweather.ui.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.brins.riceweather.utils.DeviceUtils.dip2px
import java.util.ArrayList
import kotlin.math.cos
import kotlin.math.sin

class CircleProgressView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {

    companion object {
        private val PROGRESS_START_ANGLE = 165
        private val PROGRESS_SWEEP_ANGLE = 210
        private val POINT_RADIUS = dip2px(2f)
        private val PROGRESS_STROKE_WIDTH = dip2px(10f)
        private val STEP_LIST = intArrayOf(0, 25, 50, 75, 100)
        private val STEP_ANGLE_LIST = intArrayOf(-15, 37, 90, 143, 195)
    }

    private val mProgressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mProgressBgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mHeadPointPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mStepCountPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mProgressRect: RectF? = null
    var mStep = STEP_LIST[0]
        set(value) {
            field = value
            drawStep()
        }

    private val mTextPoints = ArrayList<Point>()
    private var mSweepAngle: Float = 0f

    init {
        // 进度条画笔
        mProgressPaint.strokeCap = Paint.Cap.ROUND
        mProgressPaint.strokeWidth = PROGRESS_STROKE_WIDTH.toFloat()
        mProgressPaint.style = Paint.Style.STROKE
        // 进度背景画笔
        mProgressBgPaint.strokeCap = Paint.Cap.ROUND
        mProgressBgPaint.strokeWidth = PROGRESS_STROKE_WIDTH.toFloat()
        mProgressBgPaint.color = Color.WHITE
        mProgressBgPaint.style = Paint.Style.STROKE
        // 进度条头部原点画笔
        mHeadPointPaint.color = Color.WHITE
        // 步数画笔
        mStepCountPaint.color = -0xbb3f7e
        mStepCountPaint.textSize = dip2px(10f).toFloat()
        mStepCountPaint.typeface = Typeface.createFromAsset(
            getContext().assets,
            "fonts/DIN-Condensed-Bold.ttf"
        )
        // 进度绘制角度计算
        mSweepAngle =
            (PROGRESS_SWEEP_ANGLE * mStep / STEP_LIST[STEP_LIST.size - 1].toFloat()).toInt()
                .toFloat()
        mSweepAngle = Math.min(PROGRESS_SWEEP_ANGLE.toFloat(), mSweepAngle)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(width, measureHeightByWidth(width))
        calculateSize()
    }

    /**
     * 初始化尺寸相关参数
     */
    private fun calculateSize() {
        // 设置进度条渐变范围
        mProgressPaint.shader = LinearGradient(
            0f, 0f, width.toFloat(), 0f,
            -0x501b95, -0xa43671, Shader.TileMode.CLAMP
        )
        // 设置进度条边框大小
        val clampWidth = PROGRESS_STROKE_WIDTH / 2
        mProgressRect = RectF(
            clampWidth.toFloat(), clampWidth.toFloat(), (measuredWidth - clampWidth).toFloat(),
            (measuredWidth - clampWidth).toFloat()
        )
        // 计算步数的坐标位置(只能这样 - - )
        val stepTextRadius = (mProgressRect!!.width() / 2 - dip2px(12f)).toInt()
        val step0 = Point(
            -(stepTextRadius * cos(Math.toRadians((-STEP_ANGLE_LIST[0]).toDouble()))).toInt(),
            (stepTextRadius * sin(Math.toRadians((-STEP_ANGLE_LIST[0]).toDouble()))).toInt()
        )
        val step1 = Point(
            -(stepTextRadius * cos(Math.toRadians(STEP_ANGLE_LIST[1].toDouble()))).toInt(),
            -(stepTextRadius * sin(Math.toRadians(STEP_ANGLE_LIST[1].toDouble()))).toInt()
        )
        val step2 = Point(0, -stepTextRadius)
        val step3 = Point(
            (stepTextRadius * cos(Math.toRadians((180 - STEP_ANGLE_LIST[3]).toDouble()))).toInt(),
            -(stepTextRadius * sin(Math.toRadians((180 - STEP_ANGLE_LIST[3]).toDouble()))).toInt()
        )
        val step4 = Point(
            (stepTextRadius * cos(Math.toRadians((STEP_ANGLE_LIST[4] - 180).toDouble()))).toInt(),
            (stepTextRadius * sin(Math.toRadians((STEP_ANGLE_LIST[4] - 180).toDouble()))).toInt()
        )
        // 调整位置
        step0.x += dip2px(5f)
        step0.y += dip2px(4f)
        step1.x += dip2px(5f)
        step1.y += dip2px(8f)
        step2.x -= dip2px(3f)
        step2.y += dip2px(13f)
        step3.x -= dip2px(15f)
        step3.y += dip2px(8f)
        step4.x -= dip2px(15f)
        step4.y += dip2px(4f)
        // 好了好了
        mTextPoints.add(step0)
        mTextPoints.add(step1)
        mTextPoints.add(step2)
        mTextPoints.add(step3)
        mTextPoints.add(step4)
    }

    private fun measureHeightByWidth(width: Int): Int {
        return (width * 460 / 690f).toInt() // 根据UI提供的尺寸宽高比来算
    }

    private fun drawStep() {
        var sweepAngle = PROGRESS_SWEEP_ANGLE * mStep / STEP_LIST[STEP_LIST.size - 1].toFloat()
        sweepAngle = Math.min(PROGRESS_SWEEP_ANGLE.toFloat(), sweepAngle)
        val valueAnimator = ValueAnimator.ofFloat(sweepAngle)
        valueAnimator.duration = 1500
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.addUpdateListener { animation ->
            mSweepAngle = animation.animatedValue as Float
            postInvalidate()
        }
        valueAnimator.start()
        mSweepAngle =
            (PROGRESS_SWEEP_ANGLE * mStep / STEP_LIST[STEP_LIST.size - 1].toFloat())
        mSweepAngle = Math.min(PROGRESS_SWEEP_ANGLE.toFloat(), mSweepAngle)
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 画出进度条背景
        canvas.drawArc(
            mProgressRect, PROGRESS_START_ANGLE.toFloat(), PROGRESS_SWEEP_ANGLE.toFloat(), false,
            mProgressBgPaint
        )

        // 画进度
        canvas.drawArc(
            mProgressRect, PROGRESS_START_ANGLE.toFloat(), mSweepAngle, false,
            mProgressPaint
        )
        // 画进度白点
        canvas.translate(mProgressRect!!.centerX(), mProgressRect!!.centerY())
        canvas.save()
        canvas.rotate(mSweepAngle - 180 + PROGRESS_START_ANGLE)
        canvas.drawCircle(-mProgressRect!!.width() / 2, 0f, POINT_RADIUS.toFloat(), mHeadPointPaint)
        canvas.restore()
        // 画出步数及圆点
        for (i in STEP_LIST.indices) {
            canvas.save()
            canvas.rotate(STEP_ANGLE_LIST[i].toFloat())
            canvas.drawCircle(
                -(mProgressRect!!.width() / 2) + dip2px(12f),
                0f, POINT_RADIUS.toFloat(), mStepCountPaint
            )
            canvas.restore()
            val step = STEP_LIST[i]
            canvas.drawText(
                step.toString(), mTextPoints[i].x.toFloat(), mTextPoints[i].y.toFloat(),
                mStepCountPaint
            )
        }
    }
}