package com.nhuhuy.mythos.core.utils

import android.util.Log

object LogUtils {
    const val EXCEPTION  = "MYTHOS_EXCEPTION"
    fun exception(e: Throwable){
        Log.e(EXCEPTION,e.message.orEmpty() )
    }
}