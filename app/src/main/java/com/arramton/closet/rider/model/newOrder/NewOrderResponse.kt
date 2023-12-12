package com.arramton.closet.rider.model.newOrder

data class NewOrderResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)