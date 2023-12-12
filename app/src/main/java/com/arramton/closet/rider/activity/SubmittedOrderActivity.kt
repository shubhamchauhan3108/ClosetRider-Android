package com.arramton.closet.rider.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.adapter.SubmittedAdapter
import com.arramton.closet.rider.adapter.TimeSlotAdapter
import com.arramton.closet.rider.factory.OrderFactory
import com.arramton.closet.rider.listener.SubmittedListener
import com.arramton.closet.rider.listener.TimeSlotListener
import com.arramton.closet.rider.model.TimeSlotModel
import com.arramton.closet.rider.model.order.Data
import com.arramton.closet.rider.repository.OrderRepository
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.rider.viewModel.OrderViewModel

class SubmittedOrderActivity : AppCompatActivity() {
    private lateinit var rvTimeSlot:RecyclerView
    private lateinit var imgBack:ImageView
    private lateinit var listTime: ArrayList<TimeSlotModel>
    private lateinit var rvSubmitted:RecyclerView
    private lateinit var orderRepository: OrderRepository
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var apiInterface: ApiInterface
    private lateinit var list:List<Data>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submitted_order)
        init()
    }

    fun init(){
        listTime= arrayListOf()

        apiInterface=RetrofitBuilder.getInstance(application)!!.api
        orderRepository=OrderRepository(apiInterface,this,application)
        orderViewModel=ViewModelProvider(this,OrderFactory(orderRepository)).get(OrderViewModel::class.java)

        rvTimeSlot=findViewById(R.id.submitted_order_check_weeks_rv)
        rvTimeSlot.setHasFixedSize(false)
        rvTimeSlot.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        rvSubmitted=findViewById(R.id.submitted_order_rv)
        rvSubmitted.setHasFixedSize(false)
        rvSubmitted.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        imgBack=findViewById(R.id.submitted_back_btn)
        imgBack.setOnClickListener { onBackPressed() }


        listTime.add(TimeSlotModel("Today"))
        listTime.add(TimeSlotModel("Weekly"))
        listTime.add(TimeSlotModel("Monthly"))
        listTime.add(TimeSlotModel("Yearly"))

        val timeSlotAdapter:TimeSlotAdapter=TimeSlotAdapter(this@SubmittedOrderActivity,listTime,object :
            TimeSlotListener {
            override fun onClickTime(name: String) {
                //calling api
            }
        })

        rvTimeSlot.adapter=timeSlotAdapter

        orderViewModel.orderSubmitted.observe(this, Observer {
            if (it!=null){
                if (it.success){
                    list=it.data

                    if (!list.isEmpty()){
                        val orderSubmittedAdapter:SubmittedAdapter= SubmittedAdapter(this,list,object :SubmittedListener{
                                override fun onClick(id: String) {
                                   startActivity(Intent(this@SubmittedOrderActivity,OrderDetailsActivity::class.java).putExtra("id",id))
                                }
                            })
                        rvSubmitted.adapter=orderSubmittedAdapter
                    }else{

                    }
                }
            }
        })
        orderViewModel.submittedOrder()






    }
}