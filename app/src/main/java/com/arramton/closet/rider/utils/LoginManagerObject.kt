package com.arramton.cakingom.utils


import android.content.SharedPreferences

object LoginManagerObject {
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    var PRIVATE_MODE = 0
    // Shared preferences file name
    private const val Preference_name = "login_manager_1"
    private const val IS_LOGED_IN = "islogin"
    private const val name = "name"
    private const val emailid = "emailid"
    private const val mobilenumber = "phone"
    private const val token = "token"

   fun instance() {
//       sharedPreferences = App.getContext().getSharedPreferences(Preference_name, PRIVATE_MODE)
       editor = sharedPreferences?.edit()
   }

    fun removeSharedPreference() {

        editor?.clear()
        editor?.apply()
    }

    fun SetLoginStatus(isFirstTime: Boolean) {

        editor?.putBoolean(IS_LOGED_IN, isFirstTime)
        editor?.commit()
        println("Login Status added")
    }

    fun isLoggedIn(): String {
//        sharedPreferences = App.getContext().getSharedPreferences(Preference_name, PRIVATE_MODE)
        editor = sharedPreferences?.edit()
//        return sharedPreferences?.getBoolean(IS_LOGED_IN, true)
        return "test"
    }



    fun setname(mname: String?) {

        editor?.putString(name, mname)?.commit()
    }

    fun getname(): String? {

        return sharedPreferences?.getString(name, "")
    }



    fun setNumber(number: String?) {

        editor?.putString(mobilenumber, number)?.commit()
    }

    fun getnumber(): String? {

        return sharedPreferences?.getString(mobilenumber, "")
    }

    fun settoken(tok: String?) {

        if (editor != null) {
            editor?.putString(token, tok)?.commit()
        } else {
//            Toast.makeText(App.getContext(), "editor is null", Toast.LENGTH_SHORT).show()
        }
    }

    fun gettoken(): String? {

        //  return "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9ibG9vbWFwcC5pblwvYXBpXC9mcm9udGVuZFwvZ2V0UHJvZHVjdHMiLCJpYXQiOjE2MTg2NTk4MjYsImV4cCI6MTYyMzMyNjg2OCwibmJmIjoxNjIwNzM0ODY4LCJqdGkiOiI5RVR1WWlVODJMT0dUckVlIiwic3ViIjoxLCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.VPXgReY9FhDaYNitX6MK5CO2TLwKtF8NWT4Mwy3XKjA";
        return sharedPreferences?.getString(token, "")
    }


}