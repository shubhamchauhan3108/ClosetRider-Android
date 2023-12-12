package com.arramton.closet.rider.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class LoginManager {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public static LoginManager Instance;

    int PRIVATE_MODE = 0;

    private static final String Preference_name = "login_manager_1";

    private static final String IS_LOGED_IN = "islogin";

    private static String name = "name";

    private static String emailid = "emailid";

    private static String mobilenumber = "phone";

    private static String username = "username";

    private static String token = "token";

    private static String fcm_Token="fcm_token";

    public static String fcmTokenStatus="isFalse";


    public static LoginManager getInstance() {

        if (Instance == null) {
//            Instance = new LoginManager(App.getContext());
        }

        return Instance;
    }


    public LoginManager(Context context) {
        this.context = context;

        sharedPreferences = context.getSharedPreferences(Preference_name, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void removeSharedPreference() {
        editor.clear();
        editor.apply();
    }

    public void SetLoginStatus(boolean isFirstTime) {
        editor.putBoolean(IS_LOGED_IN, isFirstTime);
        editor.commit();
    }
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGED_IN, true);
    }

    public void setFcmTokenStatus(boolean fcmToken){
        editor.putBoolean(fcmTokenStatus,false);
    }
    public boolean isFCMToken(){
        return sharedPreferences.getBoolean(fcmTokenStatus,false);
    }
    public void setFcmToken(String fcmToken){
        editor.putString(fcm_Token,fcmToken);
        editor.putBoolean("fcmToken",true);
    }

    public String getFcmToken(){
        return sharedPreferences.getString(fcm_Token,"");
    }


    public void setname(String mname) {
        editor.putString(name, mname).commit();
    }
    public String getname() {
        return sharedPreferences.getString(name, "");
    }

    public void setEmailid(String email) {
        editor.putString(emailid, email).commit();
    }

    public String getEmailid() {
        return sharedPreferences.getString(emailid, "");

    }

    public void setNumber(String number) {
        editor.putString(mobilenumber, number).commit();
    }

    public String getnumber() {
        return sharedPreferences.getString(mobilenumber, "");

    }


    public void settoken(String tok) {
        editor.putString(token, tok).apply();
    }

    public String gettoken() {

        //  return "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9ibG9vbWFwcC5pblwvYXBpXC9mcm9udGVuZFwvZ2V0UHJvZHVjdHMiLCJpYXQiOjE2MTg2NTk4MjYsImV4cCI6MTYyMzMyNjg2OCwibmJmIjoxNjIwNzM0ODY4LCJqdGkiOiI5RVR1WWlVODJMT0dUckVlIiwic3ViIjoxLCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.VPXgReY9FhDaYNitX6MK5CO2TLwKtF8NWT4Mwy3XKjA";
        return sharedPreferences.getString(token, "");
    }

}