package com.arramton.closet.rider.model.newOrder

data class Costume(
    val category_id: Int,
    val created_at: String,
    val dry_clean_charge: Int,
    val id: Int,
    val image_url: String,
    val item_price: Int,
    val name: String,
    val updated_at: String
)