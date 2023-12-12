package com.arramton.cakingom.utils

import android.app.Application
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.utils.ApiError

class EventTracking(var apiUrl:String,var parameter:String,var response:String,var time:String,var screen:String,var application: Application) {

     var apiInterface: ApiInterface = RetrofitBuilder.getInstance(application)?.api!!
             ;
    var sheetUrl = "https://script.google.com/macros/s/AKfycbz9Z6dpG7dHigsbV9c64vytFzyXmxgVmvS2OitmyoZWuLi8J7uGpsxdWx5sD50nEPQa/exec"
    var apiError = ApiError("addItem", apiUrl, parameter, response, time, screen)

//    var call: Call<String> = apiInterface.sendApiError(sheetUrl,"addItem",apiUrl,response,time,parameter,screen)
//        .enqueue(object:retrofit2.Callback<String>{
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        })



}