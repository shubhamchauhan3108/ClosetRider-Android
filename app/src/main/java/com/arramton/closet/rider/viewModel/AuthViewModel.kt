package com.arramton.closet.rider.viewModel

import com.arramton.closet.rider.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arramton.closet.rider.model.auth.LoginResponse
import com.arramton.closet.rider.model.auth.RegisterResponse
import com.arramton.closet.rider.model.auth.verifyOTP.VerifyOTPResponse

class AuthViewModel(val authRepository: AuthRepository): ViewModel() {


    fun loginAuth(mobile:String){
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.login(mobile)
        }
    }

    val loginObservable:LiveData<LoginResponse>
        get() = authRepository.loginResponse


    fun verifyOTP(mobile:String,otp:String){
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.verifyOTP(mobile,otp)
        }
    }


    val verifyOTPObservable:LiveData<VerifyOTPResponse>
        get() = authRepository.verifyOTPResponse


    fun registerObserver(name:String,mobileNumber:String,email:String,gender:String,addressLine:String,lanMark:String,state:String,city:String,pincode:String,aadharNumber:String,adharFront:String,adharBack:String,panNumber:String,panPicture:String,bankName:String,holderName:String,accountNumber:String,bankIFSC:String,profilePick:String){
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.registerObserver(name,mobileNumber,email,gender,addressLine,lanMark,state,city,pincode,aadharNumber,adharFront,adharBack,panNumber,panPicture,bankName,holderName,accountNumber,bankIFSC,profilePick)

        }
    }


    val registerObservable:LiveData<RegisterResponse>
        get() = authRepository.registerResponse





    fun pickupOrderLoginAuth(mobile:String){
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.pickupOrderLogin(mobile)
        }
    }

    val pickupOrderLoginObservable:LiveData<LoginResponse>
        get() = authRepository.pickupOrderLoginResponse


    fun pickupOrderVerifyOTP(mobile:String,otp:String){
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.pickupOrderVerifyOTP(mobile,otp)
        }
    }


    val pickupOrderVerifyOTPObservable:LiveData<VerifyOTPResponse>
        get() = authRepository.pickupOrderVerifyOTPResponse


}