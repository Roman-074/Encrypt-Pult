package com.pult.app.utils

import android.content.SharedPreferences
import androidx.collection.arraySetOf
import com.pult.app.App

class PrefUtils {

    private val spEditor: SharedPreferences.Editor? = App.sharePref.edit()

    fun setString(key: String, value: String) {
        with(spEditor) {
            this?.putString(key, value)?.apply()
        }
    }

    fun getString(key: String): String? {
        return App.sharePref.getString(key, "")
    }






    fun setServerList(list: Set<String>){
        with(spEditor){
            this?.putStringSet("pref_list", list)?.apply()
        }
    }

    fun getServerString(): Set<String>? {
        return App.sharePref.getStringSet("pref_list", arraySetOf())
    }



}