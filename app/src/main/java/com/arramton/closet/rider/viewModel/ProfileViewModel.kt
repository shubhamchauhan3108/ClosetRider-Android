package com.arramton.closet.rider.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arramton.closet.rider.model.appSetting.AppSettingResponse
import com.arramton.closet.rider.model.home.HomePageResponse
import com.arramton.closet.rider.model.profile.ProfileResponse
import com.arramton.closet.rider.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(val profileRepository: ProfileRepository):ViewModel() {

    fun profileHomePage(){
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.profileHomeResponse()
        }
    }

    val profileHomePageResponse:LiveData<HomePageResponse>
        get() = profileRepository.homePageResponse

    fun userProfile(){
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.profileResponse()
        }
    }

    val profileResponse:LiveData<ProfileResponse>
        get() = profileRepository.profileResponse

    fun appSettingObserver(path:String){
        viewModelScope.launch(Dispatchers.IO) {
            profileRepository.appSetting(path)
        }
    }

    val appSettingObservable:LiveData<AppSettingResponse>
        get() = profileRepository.appSettingLiveData


}