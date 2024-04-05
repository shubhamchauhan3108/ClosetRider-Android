package com.arramton.closet.rider.model.earning

data class Pickup(
    val created_at: String,
    val order_reference_no: String,
    val pickup_earn: String?
)