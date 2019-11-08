package com.brins.riceweather.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.brins.riceweather.data.model.weather.Forecast

class ForecastLineView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet?) :
    View(context, attributeSet) {

    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mLineColor = Color.parseColor("#00BCD4")
    private val mStrokeWidth = 8.0f
    private val mPointRadius = 10f
    private val mYAxisFontSize = 40f
    private val mNoDataMsg = "no data"
    var forecastLists: List<Forecast>? = null


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
        val axisPaint = Paint()
        axisPaint.textSize = mYAxisFontSize
        axisPaint.color = Color.parseColor("#383838")
        //y点坐标集合
        val yPointMax = IntArray(6)
        val yPointMin = IntArray(6)
        val yInterval = ((mHeight.toFloat() - mYAxisFontSize - 2f) / 2f).toInt()

        //x点坐标集合
        val xPoints = IntArray(6)
        val xItemX = 42
        val xOffset = 50
        val xInterval = (mWidth - xOffset) / 6
        val xItemY = (mYAxisFontSize + 2 * yInterval).toInt()

        for (i in 0..5) {
            canvas.drawText(
                forecastLists!![i].temperature.max(),
                (i * xInterval + xItemX + xOffset).toFloat(), xItemY - yInterval * 2f, axisPaint
            )

            canvas.drawText(
                forecastLists!![i].temperature.min(),
                (i * xInterval + xItemX + xOffset).toFloat(), xItemY - 2f, axisPaint
            )

            yPointMax[i] = (xItemY - forecastLists!![i].temperature.max.toInt() - 3 * yInterval / 2)
            yPointMin[i] = (xItemY - forecastLists!![i].temperature.min.toInt() - yInterval / 2)
            xPoints[i] =
                ((i * xInterval + xItemX + xOffset + 20).toFloat()).toInt()


        }


        val pointPaint = Paint()

        pointPaint.color = mLineColor

        val linePaint = Paint()
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