package com.arramton.closet.rider.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.adapter.DeliveredAdapter
import com.arramton.closet.rider.factory.OrderFactory
import com.arramton.closet.rider.listener.DeliveryListener
import com.arramton.closet.rider.repository.OrderRepository
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.rider.viewModel.OrderViewModel

class TotalDeliveryActivity : AppCompatActivity() {
    private lateinit var backBtn:ImageView
    private lateinit var rvDelivery:RecyclerView

    private lateinit var viewModel: OrderViewModel
    private lateinit var orderRepository: OrderRepository
    private lateinit var apiInterface: ApiInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_delivery)
        init()
    }

    fun init(){
        backBtn=findViewById(R.id.total_delivery_back_btn)
        backBtn.setOnClickListener {
            onBackPressed()
        }

        rvDelivery=findViewById(R.id.total_delivery_rv)
        rvDelivery.setHasFixedSize(false)
        rvDelivery.layoutManager=LinearLayoutManager(this@TotalDeliveryActivity,LinearLayoutManager.VERTICAL,false)
        apiInterface= RetrofitBuilder.getInstance(application)!!.api
        orderRepository= OrderRepository(apiInterface,this,application)
        viewModel = ViewModelProvider(this, OrderFactory(orderRepository)).get(OrderViewModel::class.java)

        viewModel.orderDeliveredLiveData.observe(this) {
            if (it != null) {
                if (it.success) {
                    val adapter = DeliveredAdapter(this@TotalDeliveryActivity, it.data,object : DeliveryListener{
                        override fun onClick(id: Int) {
                            TODO("Not yet implemented")
                        }

                    })
                    rvDelivery.adapter = adapter
                }
            }
        }
        viewModel.orderDelivered("1")


    }
}