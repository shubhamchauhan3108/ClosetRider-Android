package com.arramton.closet.rider.model.auth.verifyOTP

data class Data(
    val token: String,
    val userInfo: UserInfo
)