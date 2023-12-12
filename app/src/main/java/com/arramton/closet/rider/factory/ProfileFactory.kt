package com.arramton.closet.rider.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arramton.closet.rider.repository.ProfileRepository
import com.arramton.closet.rider.viewModel.ProfileViewModel

class ProfileFactory(val profileRepository: ProfileRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            ProfileViewModel(this.profileRepository)as T
        }else{
            return throw IllegalArgumentException("View Model Not Found")
        }
    }
}