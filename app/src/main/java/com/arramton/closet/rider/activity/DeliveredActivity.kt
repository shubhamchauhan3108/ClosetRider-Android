package com.arramton.closet.rider.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.adapter.DeliveredAdapter
import com.arramton.closet.rider.adapter.TimeSlotAdapter
import com.arramton.closet.rider.factory.OrderFactory
import com.arramton.closet.rider.listener.DeliveryListener
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
    private lateinit var textTv : TextView
    private lateinit var deliveredAdapter: DeliveredAdapter

    private lateinit var backBtn : ImageView
    private var typeName = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivered)
        init()
    }

    fun init(){
        listTime= arrayListOf()

        rvTime=findViewById(R.id.delivered_rv_time)
        backBtn = findViewById(R.id.nav_customer_care_back_btn)
        textTv = findViewById(R.id.textTv)

        val title = intent.getStringExtra("delivery")

        if (title != null){
            textTv.text = title
        }

        rvTime.setHasFixedSize(false)
        rvTime.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        listTime.add(TimeSlotModel("Today"))
        listTime.add(TimeSlotModel("Weekly"))
        listTime.add(TimeSlotModel("Monthly"))
        listTime.add(TimeSlotModel("Yearly"))
        timeSlotAdapter= TimeSlotAdapter(this,listTime,object :TimeSlotListener{
            override fun onClickTime(name: String) {
                // callinmg api
                typeName = name


                if (!typeName.equals("")) {
                    if (typeName.equals("Today")) {
                        orderViewModel.orderDelivered("3","1")
                    } else if (typeName.equals("Weekly")) {
                        orderViewModel.orderDelivered("3","2")
                    } else if (typeName.equals("Monthly")) {
                        orderViewModel.orderDelivered("3","3")
                    } else {
                        orderViewModel.orderDelivered("3","4")
                    }
                }



            }
        })
        rvTime.adapter=timeSlotAdapter

        backBtn.setOnClickListener {
            onBackPressed()
        }

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
                    if (it.data != null){
                        deliveredAdapter=DeliveredAdapter(this@DeliveredActivity,
                            list as java.util.ArrayList<Data>,object :DeliveryListener{
                            override fun onClick(id: Int) {

                            }

                        })
                        rvDelivered.adapter=deliveredAdapter
                        rvDelivered.visibility = View.VISIBLE
                    } else {
                        rvDelivered.visibility = View.GONE
                    }
                } else {
                    rvDelivered.visibility = View.GONE
                }
            }
        })



//            if (typeName.equals("today")) {
                orderViewModel.orderDelivered("3","1")


//            } else if (typeName.equals("week")) {
//                orderViewModel.orderDelivered("3","2")
//            } else if (typeName.equals("this month")) {
//                orderViewModel.orderDelivered("3","3")
//            } else {
//                orderViewModel.orderDelivered("3","4")
//            }




    }
}