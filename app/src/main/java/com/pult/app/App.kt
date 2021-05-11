package com.pult.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class App: Application() {

//    companion object{
//        lateinit var sharePref: SharedPreferences
//    }

    override fun onCreate() {
        super.onCreate()

//        sharePref = applicationContext.getSharedPreferences("Shared_Pref", Context.MODE_PRIVATE)
    }

}