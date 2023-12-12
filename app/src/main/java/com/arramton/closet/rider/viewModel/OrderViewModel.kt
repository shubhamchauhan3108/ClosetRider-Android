package com.arramton.closet.rider.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arramton.closet.rider.model.deliveried.DeliveryResponse
import com.arramton.closet.rider.model.newOrder.NewOrderResponse
import com.arramton.closet.rider.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(val orderRepository: OrderRepository):ViewModel() {

    fun orderDelivered(){
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.delivered()
        }
    }

    fun newJobs() {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.newJob()
        }
    }

    val orderDeliveredLiveData:LiveData<DeliveryResponse>
        get() = orderRepository.deliveredLiveData

    val newJobLiveData:LiveData<NewOrderResponse>
        get() = orderRepository.newJobLiveData


}