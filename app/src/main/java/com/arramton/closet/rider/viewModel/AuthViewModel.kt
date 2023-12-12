package com.arramton.closet.rider.viewModel

import com.arramton.closet.rider.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arramton.closet.rider.model.auth.LoginResponse
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



}