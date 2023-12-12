package com.arramton.closet.rider.listener

import com.arramton.closet.rider.model.orderDetails.CostumesOrderItem

interface OrderDetailsListener {
    fun listAdd(list: List<CostumesOrderItem>)

}