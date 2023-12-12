package com.arramton.closet.rider.model.profile

data class ProfileResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)