package com.arramton.closet.rider.model.order

data class OrderResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)