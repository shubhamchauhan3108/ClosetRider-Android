package com.arramton.closet.rider.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.rider.model.auth.LoginResponse
import com.arramton.closet.rider.model.auth.verifyOTP.VerifyOTPResponse
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class AuthRepository(val context: Context, val apiInterface: ApiInterface, val  application: Application) {

    private val loginMutableLiveData=MutableLiveData<LoginResponse>()

    val loginResponse:LiveData<LoginResponse>
        get() = loginMutableLiveData

    private val verifyOTPMutableLiveData=MutableLiveData<VerifyOTPResponse>()

    val verifyOTPResponse:LiveData<VerifyOTPResponse>
        get() =verifyOTPMutableLiveData

    suspend fun login(mobile:String){
        val call: Call<LoginResponse> =apiInterface.login(mobile)
        call.enqueue(object : retrofit2.Callback<LoginResponse?> {
            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>

            ) {
                println("cart request "+call.request())
                println("cart response "+response)
                if (response.isSuccessful() && response.body() != null) {
                    loginMutableLiveData.postValue(response.body())
                }else{
                    val responseBody = response.errorBody()
                    try {
//                        val response1 = responseBody!!.string()
//                        val apiError = ApiErrorRestApi(call.request().url.toString(),
//                            Gson().toJson(call.request().body),
//                            Gson().toJson(response1), ApiErrorRestApi.getCurrentDateAndTime(), VerifyOTPRespository::class.java.name, application)
//                        apiError.makeAPICall();
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }

            override fun onFailure(call: retrofit2.Call<LoginResponse?>, t: Throwable) {

//                val apiError:ApiErrorRestApi = ApiErrorRestApi(call.request().url.toString(),
//                    Gson().toJson(call.request().body),
//                    Gson().toJson(t.message), ApiErrorRestApi.getCurrentDateAndTime(), AddCartRepository::class.java.name, application)
//                apiError.makeAPICall();
//                EventTracking.apiFailure(application, "resendEmailOtp", call.request().url().toString(), new Gson().toJson(call.request().body()), t.getMessage(), new LoginManager(context).getnumber(), Tools.getCurrentDateAndTime(), AccountSecurityViewModel.class.getName());

//                val eventTracking:EventTracking=EventTracking

                println("error message  = " + t.message)
            }
        })
    }

    suspend fun verifyOTP(mobile:String,otp:String){
        val call: Call<VerifyOTPResponse> =apiInterface.verifyOTP(mobile,otp)
        call.enqueue(object : retrofit2.Callback<VerifyOTPResponse?> {
            override fun onResponse(
                call: Call<VerifyOTPResponse?>,
                response: Response<VerifyOTPResponse?>

            ) {
                println("cart request "+call.request())
                println("cart response "+response)
                if (response.isSuccessful() && response.body() != null) {
                    verifyOTPMutableLiveData.postValue(response.body())
                }else{
                    val responseBody = response.errorBody()
                    try {
//                        val response1 = responseBody!!.string()
//                        val apiError = ApiErrorRestApi(call.request().url.toString(),
//                            Gson().toJson(call.request().body),
//                            Gson().toJson(response1), ApiErrorRestApi.getCurrentDateAndTime(), VerifyOTPRespository::class.java.name, application)
//                        apiError.makeAPICall();
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }

            override fun onFailure(call: retrofit2.Call<VerifyOTPResponse?>, t: Throwable) {

//                val apiError:ApiErrorRestApi = ApiErrorRestApi(call.request().url.toString(),
//                    Gson().toJson(call.request().body),
//                    Gson().toJson(t.message), ApiErrorRestApi.getCurrentDateAndTime(), AddCartRepository::class.java.name, application)
//                apiError.makeAPICall();
//                EventTracking.apiFailure(application, "resendEmailOtp", call.request().url().toString(), new Gson().toJson(call.request().body()), t.getMessage(), new LoginManager(context).getnumber(), Tools.getCurrentDateAndTime(), AccountSecurityViewModel.class.getName());

//                val eventTracking:EventTracking=EventTracking

                println("error message  = " + t.message)
            }
        })
    }

}