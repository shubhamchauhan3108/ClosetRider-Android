package com.arramton.closet.rider.model.deliveried

data class Address(
    val address_line_1: String,
    val city: String,
    val created_at: String,
    val id: Int,
    val is_primary: Int,
    val landmark: String,
    val pincode: String,
    val state: String,
    val updated_at: String,
    val user_id: Int
)