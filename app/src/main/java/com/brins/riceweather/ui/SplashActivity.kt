package com.brins.riceweather.ui

import android.content.Intent
import android.os.Bundle
import android.os.Message
import com.brins.riceweather.R
import com.brins.riceweather.utils.WeakHandler

class SplashActivity : BaseActivity(), WeakHandler.IHandler {

    private var mForceGoMain: Boolean = false
    private val AD_TIME_OUT : Long = 2000
    private val MSG_GO_MAIN = 1
    private var mHasLoaded: Boolean = false
    private val mHandler = WeakHandler(this)

    override fun handleMsg(msg: Message) {
        if (msg.what == MSG_GO_MAIN) {
            if (!mHasLoaded) {
                jump2MainSplash()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mHandler.sendEmptyMessageDelayed(MSG_GO_MAIN, AD_TIME_OUT)

    }

    override fun onResume() {
        if (mForceGoMain) {
            mHandler.removeCallbacksAndMessages(null)
            jump2MainSplash()
        }
        super.onResume()
    }

    private fun jump2MainSplash() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStop() {
        super.onStop()
        mForceGoMain = true
    }
}
