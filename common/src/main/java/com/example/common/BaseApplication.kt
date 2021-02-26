package com.example.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.BuildConfig
import com.alibaba.android.arouter.launcher.ARouter

open class BaseApplication:Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN = "40d887dee7539620e35122401bf328dc"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }


}