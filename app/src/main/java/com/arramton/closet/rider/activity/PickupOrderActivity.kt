package com.arramton.closet.rider.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.adapter.PickupOrderAdapter
import com.arramton.closet.rider.factory.OrderFactory
import com.arramton.closet.rider.repository.OrderRepository
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.rider.viewModel.OrderViewModel
import com.bumptech.glide.request.target.ThumbnailImageViewTarget

class PickupOrderActivity : AppCompatActivity() {
    private lateinit var backBtn:ImageView
    private lateinit var pickupOrderAdapter: PickupOrderAdapter

    private lateinit var apiInterface: ApiInterface
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var orderRepository: OrderRepository
    private lateinit var rvOrderPickup:RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pickup_order)
        init()
    }

    fun init(){
        backBtn=findViewById(R.id.pickup_order_back_btn)
        backBtn.setOnClickListener{onBackPressed()}
        rvOrderPickup=findViewById(R.id.pickup_order_rv)

        rvOrderPickup.setHasFixedSize(false)
        rvOrderPickup.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)


        apiInterface=RetrofitBuilder.getInstance(application)!!.api
        orderRepository=OrderRepository(apiInterface,this,application)
        orderViewModel=ViewModelProvider(this,OrderFactory(orderRepository)).get(OrderViewModel::class.java)

        orderViewModel.pickupOrderResponse.observe(this, Observer {
            if (it!=null){

                if (it.success){
                    pickupOrderAdapter= PickupOrderAdapter(this,it.data)
                    rvOrderPickup.adapter=pickupOrderAdapter

                }
            }
        })
        orderViewModel.pickupOrder()
    }
}