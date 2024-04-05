package com.arramton.closet.rider.repository

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arramton.closet.rider.model.auth.LoginResponse
import com.arramton.closet.rider.model.deliveried.DeliveryResponse
import com.arramton.closet.rider.model.earning.EarningResponse
import com.arramton.closet.rider.model.newOrder.EditNewJobResponse
import com.arramton.closet.rider.model.newOrder.NewOrderResponse
import com.arramton.closet.rider.model.newOrder.editNewOrder.EditNewOrderRequest
import com.arramton.closet.rider.model.order.OrderResponse
import com.arramton.closet.rider.model.orderDetails.OrderDetailsResponse
import com.arramton.closet.rider.model.pickupOrder.SubmitOrderResponse
import com.arramton.closet.rider.model.uploadPhoto.UploadResponse
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.rider.utils.LoginManager
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class OrderRepository(
    val apiInterface: ApiInterface,
    val context: Context,
    val application: Application
) {
    private lateinit var loginManager: LoginManager

    private var deliveredMutableLiveData = MutableLiveData<DeliveryResponse>()
    private var newJobMutableLiveData = MutableLiveData<NewOrderResponse>()
    private var acceptJobMutableLiveData=MutableLiveData<LoginResponse>()
    val acceptjobLiveData:LiveData<LoginResponse>
        get() = acceptJobMutableLiveData

    val deliveredLiveData: LiveData<DeliveryResponse>
        get() = deliveredMutableLiveData
    val newJobLiveData: LiveData<NewOrderResponse>
        get() = newJobMutableLiveData

    private var pickupOrderRepository = MutableLiveData<OrderResponse>()

    val pickupOrderLiveData: LiveData<OrderResponse>
        get() = pickupOrderRepository

    private var orderDetailsMutableLiveData = MutableLiveData<OrderDetailsResponse>()

    val orderDetailsLiveData: LiveData<OrderDetailsResponse>
        get() = orderDetailsMutableLiveData

    private var orderSubmittedMutableLiveData = MutableLiveData<OrderResponse>()
    val submittedLiveData: LiveData<OrderResponse>
        get() = orderSubmittedMutableLiveData

    private var editNewJobMutableLiveData = MutableLiveData<EditNewJobResponse>()
    val editJobLiveData: LiveData<EditNewJobResponse>
        get() = editNewJobMutableLiveData

    private var uploadPhotoMutableLiveData = MutableLiveData<UploadResponse>()
    val uploadPhotoLivaData: LiveData<UploadResponse>
        get() = uploadPhotoMutableLiveData

    private var submitPickupMutableLiveData = MutableLiveData<SubmitOrderResponse>()
    val submitPickupLiveData: LiveData<SubmitOrderResponse>
        get() = submitPickupMutableLiveData


    private var eraningMutableLiveData=MutableLiveData<EarningResponse>()
    val earningLiveData:LiveData<EarningResponse>
        get() = eraningMutableLiveData;
    suspend fun delivered(id: String) {
        loginManager = LoginManager(context)

        val call: Call<DeliveryResponse> = apiInterface.deliveryOrders(loginManager.gettoken(), id)
        call.enqueue(object : retrofit2.Callback<DeliveryResponse?> {
            override fun onResponse(
                call: Call<DeliveryResponse?>,
                response: Response<DeliveryResponse?>

            ) {
                if (response.isSuccessful() && response.body() != null) {
                    deliveredMutableLiveData.postValue(response.body())
                } else {
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

    suspend fun newJob() {
        loginManager = LoginManager(context)

        val call: Call<NewOrderResponse> = apiInterface.getNewJob(loginManager.gettoken())
        call.enqueue(object : retrofit2.Callback<NewOrderResponse?> {
            override fun onResponse(
                call: Call<NewOrderResponse?>,
                response: Response<NewOrderResponse?>

            ) {
                if (response.isSuccessful() && response.body() != null) {
                    newJobMutableLiveData.postValue(response.body())
                } else {
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

            override fun onFailure(call: retrofit2.Call<NewOrderResponse?>, t: Throwable) {

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

    suspend fun orderPickup() {
        loginManager = LoginManager(context)

        val call: Call<OrderResponse> = apiInterface.pickupOrder(loginManager.gettoken())
        call.enqueue(object : retrofit2.Callback<OrderResponse?> {
            override fun onResponse(
                call: Call<OrderResponse?>,
                response: Response<OrderResponse?>

            ) {
                if (response.isSuccessful() && response.body() != null) {
                    pickupOrderRepository.postValue(response.body())
                } else {
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

            override fun onFailure(call: retrofit2.Call<OrderResponse?>, t: Throwable) {

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

    suspend fun orderSubmitted() {
        loginManager = LoginManager(context)

        val call: Call<OrderResponse> = apiInterface.submitted(loginManager.gettoken())
        call.enqueue(object : retrofit2.Callback<OrderResponse?> {
            override fun onResponse(
                call: Call<OrderResponse?>,
                response: Response<OrderResponse?>

            ) {
                if (response.isSuccessful() && response.body() != null) {
                    orderSubmittedMutableLiveData.postValue(response.body())
                } else {
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

            override fun onFailure(call: retrofit2.Call<OrderResponse?>, t: Throwable) {

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

    suspend fun orderDetails(id: String) {
        loginManager = LoginManager(context)

        val call: Call<OrderDetailsResponse> =
            apiInterface.orderDetails(loginManager.gettoken(), id)
        call.enqueue(object : retrofit2.Callback<OrderDetailsResponse?> {
            override fun onResponse(
                call: Call<OrderDetailsResponse?>,
                response: Response<OrderDetailsResponse?>

            ) {
                if (response.isSuccessful() && response.body() != null) {
                    orderDetailsMutableLiveData.postValue(response.body())
                } else {
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

            override fun onFailure(call: retrofit2.Call<OrderDetailsResponse?>, t: Throwable) {

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

    suspend fun editNewJob(editNewOrderRequest: EditNewOrderRequest) {

        loginManager = LoginManager(context)

        val call: Call<EditNewJobResponse> =
            apiInterface.editNewJob(loginManager.gettoken(), editNewOrderRequest)
        call.enqueue(object : retrofit2.Callback<EditNewJobResponse?> {
            override fun onResponse(
                call: Call<EditNewJobResponse?>,
                response: Response<EditNewJobResponse?>

            ) {
                if (response.isSuccessful() && response.body() != null) {
                    editNewJobMutableLiveData.postValue(response.body())
                } else {
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

            override fun onFailure(call: retrofit2.Call<EditNewJobResponse?>, t: Throwable) {

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


    suspend fun uploadPhoto(image: MultipartBody.Part) {

        loginManager = LoginManager(context)

        val call: Call<UploadResponse> = apiInterface.uploadPhoto(loginManager.gettoken(), image)
        call.enqueue(object : retrofit2.Callback<UploadResponse?> {
            override fun onResponse(
                call: Call<UploadResponse?>,
                response: Response<UploadResponse?>

            ) {
                if (response.isSuccessful() && response.body() != null) {
                    uploadPhotoMutableLiveData.postValue(response.body())
                } else {
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

            override fun onFailure(call: retrofit2.Call<UploadResponse?>, t: Throwable) {

                Toast.makeText(application, "Error", Toast.LENGTH_SHORT).show()
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

    suspend fun pickupOrderSubmit(id: String) {

        loginManager = LoginManager(context)

        val call: Call<SubmitOrderResponse> = apiInterface.submitPickupOrder(loginManager.gettoken(), id)
        call.enqueue(object : retrofit2.Callback<SubmitOrderResponse?> {
            override fun onResponse(
                call: Call<SubmitOrderResponse?>,
                response: Response<SubmitOrderResponse?>

            ) {
                if (response.isSuccessful() && response.body() != null) {
                    submitPickupMutableLiveData.postValue(response.body())
                } else {
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

            override fun onFailure(call: retrofit2.Call<SubmitOrderResponse?>, t: Throwable) {

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
    suspend fun acceptOrder(id: String) {

        loginManager = LoginManager(context)

        val call: Call<LoginResponse> = apiInterface.acceptJob(loginManager.gettoken(), id)
        call.enqueue(object : retrofit2.Callback<LoginResponse?> {
            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>

            ) {
                if (response.isSuccessful() && response.body() != null) {
                    acceptJobMutableLiveData.postValue(response.body())
                } else {
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
    suspend fun earningOrder(id: String) {

        loginManager = LoginManager(context)

        val call: Call<EarningResponse> = apiInterface.riderEarning(loginManager.gettoken(), id)
        call.enqueue(object : retrofit2.Callback<EarningResponse?> {
            override fun onResponse(
                call: Call<EarningResponse?>,
                response: Response<EarningResponse?>

            ) {
                if (response.isSuccessful() && response.body() != null) {
                    eraningMutableLiveData.postValue(response.body())
                } else {
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

            override fun onFailure(call: retrofit2.Call<EarningResponse?>, t: Throwable) {

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