package com.arramton.closet.rider.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arramton.closet.rider.repository.OrderRepository
import com.arramton.closet.rider.viewModel.OrderViewModel

class OrderFactory(val orderRepository: OrderRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(OrderViewModel::class.java)){
            OrderViewModel(this.orderRepository) as T
        }else{
            return throw IllegalArgumentException("View Model Not Working")
        }
    }
}