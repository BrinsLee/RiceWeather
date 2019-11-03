package com.brins.riceweather.ui

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.brins.riceweather.R
import com.jaeger.library.StatusBarUtil

abstract class BaseActivity : AppCompatActivity() {

    protected open var TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*        if (Build.VERSION.SDK_INT >= 23) {
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = getColor(R.color.translate)
        }*/
    }

    protected open fun getOffsetView(): View? {
        return null
    }

    protected fun isStatusBarTranslucent(): Boolean {
        return true
    }

    protected fun setStatusBarTranslucent() {
        if (!isStatusBarTranslucent()) {
            return
        }
        StatusBarUtil.setTranslucentForImageView(this, 0, getOffsetView())
    }

//    protected abstract fun getLayoutResId(): Int

}
