package com.arramton.closet.rider.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arramton.closet.rider.model.auth.LoginResponse
import com.arramton.closet.rider.model.deliveried.DeliveryResponse
import com.arramton.closet.rider.model.earning.EarningResponse
import com.arramton.closet.rider.model.newOrder.EditNewJobResponse
import com.arramton.closet.rider.model.newOrder.NewOrderResponse
import com.arramton.closet.rider.model.newOrder.editNewOrder.EditNewOrderRequest
import com.arramton.closet.rider.model.order.OrderResponse
import com.arramton.closet.rider.model.orderDetails.OrderDetailsResponse
import com.arramton.closet.rider.model.pickupOrder.SubmitOrderResponse
import com.arramton.closet.rider.model.uploadPhoto.UploadResponse
import com.arramton.closet.rider.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class OrderViewModel(val orderRepository: OrderRepository):ViewModel() {

    fun orderDelivered(id: String, type: String?){
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.delivered(id,type!!)
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

    fun submittedOrder(){
        viewModelScope.launch(Dispatchers.IO) { orderRepository.orderSubmitted() }
    }
    val orderSubmitted:LiveData<OrderResponse>
        get() = orderRepository.submittedLiveData

    fun editNewOrderObservable(editNewOrderRequest: EditNewOrderRequest){
        viewModelScope.launch(Dispatchers.IO) { orderRepository.editNewJob(editNewOrderRequest) }

    }

    val editNewJobObserver:LiveData<EditNewJobResponse>
        get() = orderRepository.editJobLiveData

    fun uploadObservable(image: MultipartBody.Part){
        viewModelScope.launch(Dispatchers.IO){
            orderRepository.uploadPhoto(image)
        }
    }

    val uploadObserver:LiveData<UploadResponse>
        get() = orderRepository.uploadPhotoLivaData

    fun submitPickupOrderObservable(id:String){
        viewModelScope.launch(Dispatchers.IO){
            orderRepository.pickupOrderSubmit(id)
        }
    }

    val submitPickupOrderObserver:LiveData<SubmitOrderResponse>
        get() = orderRepository.submitPickupLiveData


    fun acceptOrderObservable(id:String){
        viewModelScope.launch(Dispatchers.IO){
            orderRepository.acceptOrder(id)
        }
    }

    val acceptOrderObserver:LiveData<LoginResponse>
        get() = orderRepository.acceptjobLiveData


    fun earningObservable(id: String){
        viewModelScope.launch(Dispatchers.IO){
            orderRepository.earningOrder(id)
        }
    }

    val earningObserver:LiveData<EarningResponse>
        get() = orderRepository.earningLiveData




}