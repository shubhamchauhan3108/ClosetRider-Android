package com.arramton.closet.rider.model.auth

data class LoginResponse(
    val `data`: Any,
    val message: String,
    val success: Boolean
)