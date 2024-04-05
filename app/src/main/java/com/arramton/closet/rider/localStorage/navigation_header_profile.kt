package com.arramton.closet.rider.localStorage

import android.content.Context
import android.content.SharedPreferences


class navigation_header_profile(val context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("databaseHeader", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit();


    fun saveData(name: String, number: String,profileImage: String) {
        editor.putString("name", name)
        editor.putString("number", number)
        editor.putString("profileImage",profileImage)
        editor.apply()
    }

    fun getName(): String? {
        return sharedPreferences.getString("name", "null")
    }

    fun getImage(): String? {
        return sharedPreferences.getString("profileImage", "null")
    }

    fun getNumber(): String? {
        return sharedPreferences.getString("number", "null")
    }

}
