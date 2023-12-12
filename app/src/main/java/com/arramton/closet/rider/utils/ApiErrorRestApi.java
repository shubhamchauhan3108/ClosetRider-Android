package com.arramton.closet.rider.utils;

import android.app.Application;

import com.arramton.closet.rider.restService.ApiInterface;
import com.arramton.closet.restService.RetrofitBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ApiErrorRestApi {
    String apiUrl; String parameter; String response; String time; String screen; Application application;
    public ApiErrorRestApi(String apiUrl, String parameter, String response, String time, String screen, Application application) {
        this.apiUrl = apiUrl;
        this.parameter = parameter;
        this.response = response;
        this.time = time;
        this.screen = screen;
        this.application = application;
    }
    public void makeAPICall() {
        String url = "https://script.google.com/macros/s/AKfycbz9Z6dpG7dHigsbV9c64vytFzyXmxgVmvS2OitmyoZWuLi8J7uGpsxdWx5sD50nEPQa/exec";
       ApiInterface retrofitBuilder  = new RetrofitBuilder(application).getApi();
//       Call<String> call =  retrofitBuilder.sendApiError(url, "addItem", apiUrl, response, time, parameter, screen);
//       call.enqueue(new Callback<String>() {
//           @Override
//           public void onResponse(Call<String> call, Response<String> response) {
//               System.out.println(response.body());
//           }
//
//           @Override
//           public void onFailure(Call<String> call, Throwable t) {
//
//           }
//       });
    }
    public static String getCurrentDateAndTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
