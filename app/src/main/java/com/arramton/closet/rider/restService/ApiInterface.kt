package com.arramton.closet.rider.restService

import com.arramton.closet.rider.model.auth.LoginResponse
import com.arramton.closet.rider.model.auth.RegisterResponse
import com.arramton.closet.rider.model.auth.verifyOTP.VerifyOTPResponse
import com.arramton.closet.rider.model.deliveried.DeliveryResponse
import com.arramton.closet.rider.model.home.HomePageResponse
import com.arramton.closet.rider.model.newOrder.EditNewJobResponse
import com.arramton.closet.rider.model.newOrder.NewOrderResponse
import com.arramton.closet.rider.model.newOrder.editNewOrder.EditNewOrderRequest
import com.arramton.closet.rider.model.order.OrderResponse
import com.arramton.closet.rider.model.orderDetails.OrderDetailsResponse
import com.arramton.closet.rider.model.pickupOrder.SubmitOrderResponse
import com.arramton.closet.rider.model.profile.ProfileResponse
import com.arramton.closet.rider.model.uploadPhoto.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


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
    fun deliveryOrders(@Header("Authorization") token:String,@Query("status") status:String):Call<DeliveryResponse>

    @GET("api/pickup_user/getNewJobs")
    fun getNewJob(@Header("Authorization") token:String): Call<NewOrderResponse>

    @GET("api/pickup_user/getPickupOrder")
    fun pickupOrder(@Header("Authorization") token: String):Call<OrderResponse>

    @GET("api/order/getOrderDetails/{id}")
    fun orderDetails(@Header("Authorization") token: String,@Path("id") id:String):Call<OrderDetailsResponse>

    @GET("api/pickup_user/getOrderSubmitted/")
    fun submitted(@Header("Authorization") token: String):Call<OrderResponse>

    @POST("api/pickup_user/updateOrder")
    fun editNewJob(@Header("Authorization") token:String,@Body editNewJobRequest: EditNewOrderRequest):Call<EditNewJobResponse>
    @Multipart
    @POST("api/uploadImage")
    fun uploadPhoto(@Header("Authorization") token:String,@Part image: MultipartBody.Part):Call<UploadResponse>

    @POST("api/pickup_user/submitOrderAtStore/{id}")
    fun submitPickupOrder(@Header("Authorization") token:String,@Path("id") id: String):Call<SubmitOrderResponse>
    @FormUrlEncoded

    @POST("api/pickup_auth/register")
    fun register(
                 @Field("name") name: String,
                 @Field("mobile_no") mobilNo:String,
                 @Field("email") email:String,
                 @Field("gender") gender:String,
                 @Field("address_line_1") addressLine:String,
                 @Field("landmark") landMark:String,
                 @Field("state") state:String,
                 @Field("city") city:String,
                 @Field("pincode") pincode:String,
                 @Field("aadhaar_number") aadharNumber:String,
                 @Field("aadhaar_front") aadharFront:String,
                 @Field("aadhaar_back") aadhaarBack:String,
                 @Field("pan_number") panCard:String,
                 @Field ("pan_picture") panPicture: String,
                 @Field("bank_name") bankName:String,
                 @Field("bank_holder_name") bankHolderName:String,
                 @Field("bank_account_no") bankAccountNo:String,
                 @Field("bank_ifsc") bankIFSC:String,
                 @Field("profile_pic") profilePick:String):Call<RegisterResponse>



}