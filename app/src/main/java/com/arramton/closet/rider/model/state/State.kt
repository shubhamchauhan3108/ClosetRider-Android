package com.arramton.closet.rider.model.state

data class State(
    val cities: List<City>,
    val id: Int,
    val latitude: String,
    val longitude: String,
    val name: String,
    val state_code: String,
    val type: String
)