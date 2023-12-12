package com.arramton.closet.rider.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arramton.closet.rider.repository.AuthRepository
import com.arramton.closet.rider.viewModel.AuthViewModel

class AuthFactory(val authRepository: AuthRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AuthViewModel::class.java)){
            AuthViewModel(this.authRepository) as T
        }else{
            throw  IllegalArgumentException("View Model Not Found")
        }
    }
}