package com.arramton.closet.rider.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arramton.closet.rider.model.deliveried.DeliveryResponse
import com.arramton.closet.rider.model.order.OrderResponse
import com.arramton.closet.rider.model.orderDetails.OrderDetailsResponse
import com.arramton.closet.rider.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(val orderRepository: OrderRepository):ViewModel() {

    fun orderDelivered(){
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.delivered()
        }
    }

    val orderDeliveredLiveData:LiveData<DeliveryResponse>
        get() = orderRepository.deliveredLiveData

    fun pickupOrder(){
        viewModelScope.launch(Dispatchers.IO) { orderRepository.orderPickup() }
    }

    val pickupOrderResponse:LiveData<OrderResponse>
        get() = orderRepository.pickupOrderLiveData

    fun orderDetails(id:String){
        viewModelScope.launch(Dispatchers.IO) { orderRepository.orderDetails(id) }
    }

    val orderDetailsLiveData:LiveData<OrderDetailsResponse>
        get() = orderRepository.orderDetailsLiveData
}