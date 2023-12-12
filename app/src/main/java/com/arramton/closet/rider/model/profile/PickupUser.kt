package com.arramton.closet.rider.model.profile

data class PickupUser(
    val aadhaar_back_picture: String,
    val aadhaar_front_picture: String,
    val aadhaar_number: String,
    val bank_account_no: Long,
    val bank_holder_name: String,
    val bank_ifsc: String,
    val bank_name: String,
    val created_at: String,
    val id: Int,
    val is_active: Int,
    val is_registered: Int,
    val pan_number: String,
    val pan_picture: String,
    val profile_pic: String,
    val updated_at: String
)