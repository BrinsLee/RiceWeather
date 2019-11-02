package com.brins.riceweather.utils

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference


class WeakHandler(handler : IHandler) : Handler() {

    interface IHandler{
        fun handleMsg(msg : Message)
    }

    private val mRef: WeakReference<IHandler> = WeakReference(handler)

    override fun handleMessage(msg: Message?) {
        var handler = mRef.get()
        if (handler != null && msg != null){
            handler.handleMsg(msg)
        }
    }
}