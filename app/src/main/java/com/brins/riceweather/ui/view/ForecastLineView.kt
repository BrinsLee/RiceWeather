package com.brins.riceweather.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.brins.riceweather.data.model.weather.Weather

class ForecastLineView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet?) :
    View(context, attributeSet) {

    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mLineColor = Color.parseColor("#00BCD4")
    private val mStrokeWidth = 8.0f
    private val mPointRadius = 10f
    private val mYAxisFontSize = 40f
    private val mNoDataMsg = "no data"
    var forecastLists: List<Weather>? = null
    private var TAG = this::class.java.simpleName
    private val yPointMax = IntArray(6)
    private val yPointMin = IntArray(6)
    private val xPoints = FloatArray(6)
    private val pointPaint = Paint()
    private val linePaint = Paint()





    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize
        } else require(widthMode != MeasureSpec.AT_MOST) { "width must be EXACTLY,you should set like android:width=\"200dp\"" }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize
        } else require(widthMode != MeasureSpec.AT_MOST) { "width must be EXACTLY,you should set like android:width=\"200dp\"" }
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d(TAG, "left:$left")
        Log.d(TAG, "top:$top")
        Log.d(TAG, "right:$right")
        Log.d(TAG, "bottom:$bottom")

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        requireNotNull(forecastLists) {
            val axisPaint = Paint()
            axisPaint.textSize = mYAxisFontSize
            axisPaint.color = Color.parseColor("#3F51B5")
            val textLength = axisPaint.measureText(mNoDataMsg).toInt()
            canvas.drawText(
                mNoDataMsg,
                (mWidth / 2 - textLength / 2).toFloat(),
                (mHeight / 2).toFloat(),
                axisPaint
            )
            return
        }
        Log.d(TAG, "$mWidth")
        Log.d(TAG, "$mHeight")

        val axisPaint = Paint()
        axisPaint.textSize = mYAxisFontSize
        axisPaint.isAntiAlias = true
        axisPaint.color = Color.parseColor("#383838")
        //y点坐标集合

        val yInterval = ((mHeight.toFloat() - mYAxisFontSize - 2f) / 2f).toInt()

        //x点坐标集合
        val xItemX = mWidth / 12
        Log.d(TAG, "xItem,$xItemX")
        val xOffset = (mWidth - xItemX) / 6f + 5f
        Log.d(TAG, "xOffset,$xOffset")

        val xItemY = (mYAxisFontSize + 2 * yInterval).toInt()
        Log.d(TAG, "xItemY,$xItemY")


        for (i in 0..5) {
            canvas.drawText(
                forecastLists!![i + 1].maxTemp(),
                (i * (xOffset) + xItemX), xItemY - yInterval * 2f, axisPaint
            )

            canvas.drawText(
                forecastLists!![i + 1].minTemp(),
                (i * (xOffset) + xItemX), xItemY - 2f, axisPaint
            )

            yPointMax[i] = (xItemY - forecastLists!![i + 1].maxTempInt() * 2 - 4 * yInterval / 3)
            yPointMin[i] = (xItemY - forecastLists!![i + 1].minTempInt() * 2 - yInterval / 2)
            xPoints[i] = (i * xOffset + xItemX + 18)


        }



        pointPaint.color = mLineColor

        linePaint.color = mLineColor
        linePaint.isAntiAlias = true
        //设置线条宽度
        linePaint.strokeWidth = mStrokeWidth
        pointPaint.style = Paint.Style.FILL

        for (i in 0..5) {
            //画点
            canvas.drawCircle(
                xPoints[i].toFloat(),
                yPointMax[i].toFloat(),
                mPointRadius,
                pointPaint
            )

            canvas.drawCircle(
                xPoints[i].toFloat(),
                yPointMin[i].toFloat(),
                mPointRadius,
                pointPaint
            )
            if (i > 0) {
                canvas.drawLine(
                    xPoints[i - 1].toFloat(),
                    yPointMax[i - 1].toFloat(),
                    xPoints[i].toFloat(),
                    yPointMax[i].toFloat(),
                    linePaint
                )

                canvas.drawLine(
                    xPoints[i - 1].toFloat(),
                    yPointMin[i - 1].toFloat(),
                    xPoints[i].toFloat(),
                    yPointMin[i].toFloat(),
                    linePaint
                )
            }
        }

    }
}