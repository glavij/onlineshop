package com.hamid.onlineshop

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.hamid.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class MyApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin(
            config = {
                androidContext(this@MyApplication)
            }
        )
        Firebase.initialize(this)
    }
}