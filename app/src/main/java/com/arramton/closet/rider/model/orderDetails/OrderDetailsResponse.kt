package com.arramton.closet.rider.model.orderDetails

import com.arramton.closet.rider.model.orderDetails.Data

data class OrderDetailsResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)