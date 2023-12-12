package com.arramton.closet.rider.model.order

data class Address(
    val address_line_1: Int,
    val city: String,
    val created_at: String,
    val id: Int,
    val is_primary: Int,
    val landmark: String,
    val pincode: Int,
    val state: String,
    val updated_at: String,
    val user_id: Int
)