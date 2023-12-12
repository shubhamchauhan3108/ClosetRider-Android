package com.arramton.closet.rider.model.auth.verifyOTP

data class VerifyOTPResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)