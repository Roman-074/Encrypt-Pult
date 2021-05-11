package com.pult.app.utils

import android.content.SharedPreferences
import androidx.collection.arraySetOf
import com.pult.ui.activity.MainActivity

class PrefUtils {

    private val spEditor: SharedPreferences.Editor? = MainActivity.sharePref.edit()

    fun setString(key: String, value: String) {
        with(spEditor) {
            this?.putString(key, value)?.apply()
        }
    }

    fun getString(key: String): String? {
        return MainActivity.sharePref.getString(key, "")
    }






    fun setServerList(list: Set<String>){
        with(spEditor){
            this?.putStringSet("pref_list", list)?.apply()
        }
    }

    fun getServerString(): Set<String>? {
        return MainActivity.sharePref.getStringSet("pref_list", arraySetOf())
    }


    //
    fun setDNSRequest(value: String) {
        with(spEditor) {
            this?.putString("DNS_request", value)?.apply()
        }
    }

    fun getDNSRequest(): String? {
        return MainActivity.sharePref.getString("DNS_request", "")
    }

}