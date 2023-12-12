package com.arramton.closet.rider.model.home

data class Data(
    val date: String,
    val newJobCount: Int,
    val orderDeliveredCount: Int,
    val orderSubmittedCount: Int,
    val pickupJobCount: Int,
    val todayOrderDeliveredCount: Int,
    val totalOrderDeliveredCount: Int,
    val user: String
)