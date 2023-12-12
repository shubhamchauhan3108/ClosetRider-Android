package com.arramton.closet.rider.model.deliveried

data class DeliveryResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)