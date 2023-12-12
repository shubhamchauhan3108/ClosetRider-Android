package com.arramton.closet.rider.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arramton.closet.rider.adapter.DeliveredAdapter
import com.arramton.closet.rider.model.auth.LoginResponse
import com.arramton.closet.rider.model.deliveried.DeliveryResponse
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.rider.utils.LoginManager
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class OrderRepository(val apiInterface: ApiInterface,val context: Context,val application: Application) {
    private lateinit var loginManager: LoginManager

    private  var deliveredMutableLiveData=MutableLiveData<DeliveryResponse>()

    val deliveredLiveData:LiveData<DeliveryResponse>
        get() = deliveredMutableLiveData

    suspend fun delivered(){
        loginManager= LoginManager(context)

        val call: Call<DeliveryResponse> =apiInterface.deliveryOrders(loginManager.gettoken())
        call.enqueue(object : retrofit2.Callback<DeliveryResponse?> {
            override fun onResponse(
                call: Call<DeliveryResponse?>,
                response: Response<DeliveryResponse?>

            ) {               if (response.isSuccessful() && response.body() != null) {
                    deliveredMutableLiveData.postValue(response.body())
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

            override fun onFailure(call: retrofit2.Call<DeliveryResponse?>, t: Throwable) {

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