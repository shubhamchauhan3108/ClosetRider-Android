package com.arramton.closet.rider.model.profile

data class Data(
    val address: Address,
    val created_at: String,
    val current_team_id: Any,
    val email: String,
    val email_verified_at: Any,
    val gender: String,
    val id: Int,
    val mobile_no: Long,
    val name: String,
    val otp: Any,
    val otp_time: String,
    val pickup_user: PickupUser,
    val pickup_user_id: Int,
    val profile_photo_path: Any,
    val two_factor_confirmed_at: Any,
    val updated_at: String
)