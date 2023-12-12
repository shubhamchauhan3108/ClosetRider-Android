package com.arramton.closet.rider.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.rider.model.home.HomePageResponse
import com.arramton.closet.rider.model.profile.ProfileResponse
import com.arramton.closet.rider.utils.LoginManager
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class ProfileRepository(val context: Context, val apiInterface: ApiInterface, val application: Application) {
    private lateinit var loginManager: LoginManager
    private val profileMutableLiveData=MutableLiveData<ProfileResponse>()
    val profileResponse:LiveData<ProfileResponse>
        get() = profileMutableLiveData

    private val homePageProfileMutableLiveData=MutableLiveData<HomePageResponse>()

    val homePageResponse:LiveData<HomePageResponse>
        get() = homePageProfileMutableLiveData


    suspend fun profileResponse(){
        loginManager=LoginManager(context)
        val call: Call<ProfileResponse> =apiInterface.profile(loginManager.gettoken())
        call.enqueue(object : retrofit2.Callback<ProfileResponse?> {
            override fun onResponse(
                call: Call<ProfileResponse?>,
                response: Response<ProfileResponse?>

            ) {
                println("cart request "+call.request())
                println("cart response "+response)
                if (response.isSuccessful() && response.body() != null) {
                    profileMutableLiveData.postValue(response.body())
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

            override fun onFailure(call: retrofit2.Call<ProfileResponse?>, t: Throwable) {

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
    suspend fun profileHomeResponse(){
        loginManager=LoginManager(context)
        val call: Call<HomePageResponse> =apiInterface.homePage(loginManager.gettoken())
        call.enqueue(object : retrofit2.Callback<HomePageResponse?> {
            override fun onResponse(
                call: Call<HomePageResponse?>,
                response: Response<HomePageResponse?>

            ) {
                println("cart request "+call.request())
                println("cart response "+response)
                if (response.isSuccessful() && response.body() != null) {
                    homePageProfileMutableLiveData.postValue(response.body())
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

            override fun onFailure(call: retrofit2.Call<HomePageResponse?>, t: Throwable) {

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