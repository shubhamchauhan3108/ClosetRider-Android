package com.arramton.closet.rider.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.adapter.DeliveredAdapter
import com.arramton.closet.rider.adapter.TimeSlotAdapter
import com.arramton.closet.rider.factory.OrderFactory
import com.arramton.closet.rider.listener.TimeSlotListener
import com.arramton.closet.rider.model.TimeSlotModel
import com.arramton.closet.rider.model.deliveried.Data
import com.arramton.closet.rider.repository.OrderRepository
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.rider.viewModel.OrderViewModel

class DeliveredActivity : AppCompatActivity() {
    private lateinit var rvTime:RecyclerView
    private lateinit var timeSlotListener: TimeSlotListener
    private lateinit var timeSlotAdapter: TimeSlotAdapter
    private lateinit var listTime: ArrayList<TimeSlotModel>
    private lateinit var rvDelivered:RecyclerView
    private lateinit var orderRepository: OrderRepository
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var apiInterface: ApiInterface
    private lateinit var list: List<Data>
    private lateinit var deliveredAdapter: DeliveredAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivered)
        init()
    }

    fun init(){
        listTime= arrayListOf()

        rvTime=findViewById(R.id.delivered_rv_time)
        rvTime.setHasFixedSize(false)
        rvTime.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        listTime.add(TimeSlotModel("Today"))
        listTime.add(TimeSlotModel("Weekly"))
        listTime.add(TimeSlotModel("Monthly"))
        listTime.add(TimeSlotModel("Yearly"))
        timeSlotAdapter= TimeSlotAdapter(this,listTime,object :TimeSlotListener{
            override fun onClickTime(name: String) {
                // callinmg api
            }
        })
        rvTime.adapter=timeSlotAdapter

        rvDelivered=findViewById(R.id.delivered_rv)
        rvDelivered.setHasFixedSize(false)
        rvDelivered.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        apiInterface=RetrofitBuilder.getInstance(application)!!.api
        orderRepository= OrderRepository(apiInterface,this,application)
        orderViewModel=ViewModelProvider(this,OrderFactory(orderRepository)).get(OrderViewModel::class.java)
        orderViewModel.orderDeliveredLiveData.observe(this, Observer {
            if (it!=null){
                if (it.success){
                    list=it.data
                    if (!list.isEmpty()){
                        deliveredAdapter=DeliveredAdapter(this@DeliveredActivity,list)
                        rvDelivered.adapter=deliveredAdapter
                    }
                }
            }
        })
        orderViewModel.orderDelivered()

    }
}