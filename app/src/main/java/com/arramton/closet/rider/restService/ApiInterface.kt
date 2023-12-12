package com.arramton.closet.rider.restService

import com.arramton.closet.rider.model.auth.LoginResponse
import com.arramton.closet.rider.model.auth.verifyOTP.VerifyOTPResponse
import com.arramton.closet.rider.model.deliveried.DeliveryResponse
import com.arramton.closet.rider.model.home.HomePageResponse
import com.arramton.closet.rider.model.newOrder.NewOrderResponse
import com.arramton.closet.rider.model.profile.ProfileResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiInterface {

    @FormUrlEncoded
    @POST("api/pickup_auth/login")
    fun login(@Field("mobile_no") phone:String):Call<LoginResponse>

    @FormUrlEncoded
    @POST("api/pickup_auth/verifyOtp")
    fun verifyOTP(@Field("mobile_no") phone: String,@Field("otp") otp:String):Call<VerifyOTPResponse>

    @GET("api/pickup_user/getHomePageData")
    fun homePage(@Header("Authorization")token: String):Call<HomePageResponse>

    @GET("api/pickup_user/profile")
    fun profile(@Header("Authorization") token:String):Call<ProfileResponse>

    @GET("api/pickup_user/getDeliveryOrders")
    fun deliveryOrders(@Header("Authorization") token:String):Call<DeliveryResponse>

    @GET("api/pickup_user/getNewJobs")
    fun getNewJob(@Header("Authorization") token:String): Call<NewOrderResponse>


}