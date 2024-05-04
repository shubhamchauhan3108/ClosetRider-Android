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
import com.arramton.closet.rider.adapter.EarningAdapter
import com.arramton.closet.rider.adapter.TimeSlotAdapter
import com.arramton.closet.rider.factory.OrderFactory
import com.arramton.closet.rider.listener.TimeSlotListener
import com.arramton.closet.rider.model.TimeSlotModel
import com.arramton.closet.rider.model.earning.AllEarningData
import com.arramton.closet.rider.model.earning.Delivery
import com.arramton.closet.rider.model.earning.Pickup
import com.arramton.closet.rider.repository.OrderRepository
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.rider.viewModel.OrderViewModel

class TotalEarnActivity : AppCompatActivity() {
    private lateinit var rvTotalEarn:RecyclerView
    private lateinit var rvTime:RecyclerView
    private lateinit var listTime: ArrayList<TimeSlotModel>

    private lateinit var backBtn: ImageView
    private lateinit var toolbarText : TextView

    private lateinit var orderRepository: OrderRepository
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var apiInterface: ApiInterface

    private  var status:String=""

    private lateinit var listEarning: ArrayList<AllEarningData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_earn)
        init()
    }

    fun init(){

        status=intent.getStringExtra("status").toString()
        toolbarText = findViewById(R.id.titleTv)
        apiInterface= RetrofitBuilder.getInstance(application)!!.api
        orderRepository=OrderRepository(apiInterface,this,application)
        orderViewModel= ViewModelProvider(this, OrderFactory(orderRepository)).get(OrderViewModel::class.java)

        listTime= arrayListOf()
        listEarning= arrayListOf()
        rvTime=findViewById(R.id.total_earn_time_rv)
        rvTime.setHasFixedSize(false)
        rvTime.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        val title = intent.getStringExtra("today")

        if (title != null){
            toolbarText.text = title
        }



        rvTotalEarn=findViewById(R.id.total_earn_rv)
        backBtn = findViewById(R.id.nav_customer_care_back_btn)

        rvTotalEarn.setHasFixedSize(false)
        rvTotalEarn.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)



        backBtn.setOnClickListener {
            onBackPressed()
        }

        listTime.add(TimeSlotModel("Today"))
        listTime.add(TimeSlotModel("Weekly"))
        listTime.add(TimeSlotModel("Monthly"))
        listTime.add(TimeSlotModel("Yearly"))

        if(status.equals("today")){
            rvTime.visibility=View.GONE

            orderViewModel.earningObservable("1")

        }else{
            rvTime.visibility=View.VISIBLE

        }

        val timeSlotAdapter:TimeSlotAdapter=TimeSlotAdapter(this@TotalEarnActivity,listTime,object :TimeSlotListener{
            override fun onClickTime(name: String) {

                if (name.equals("Today")){
                    listEarning.clear()
                    val earningAdapter:EarningAdapter= EarningAdapter(listEarning, this@TotalEarnActivity)
                    rvTotalEarn.adapter=earningAdapter;
                    orderViewModel.earningObservable("1")

                }else if (name.equals("Weekly")){
                    listEarning.clear()

                    val earningAdapter:EarningAdapter= EarningAdapter(listEarning, this@TotalEarnActivity)
                    rvTotalEarn.adapter=earningAdapter;
                    orderViewModel.earningObservable("2")

                }else if (name.equals("Monthly")){
                    listEarning.clear()
                    val earningAdapter:EarningAdapter= EarningAdapter(listEarning, this@TotalEarnActivity)
                    rvTotalEarn.adapter=earningAdapter;

                    orderViewModel.earningObservable("3")

                } else if (name.equals("Yearly")){
                    listEarning.clear()
                    val earningAdapter:EarningAdapter= EarningAdapter(listEarning, this@TotalEarnActivity)
                    rvTotalEarn.adapter=earningAdapter;

                    orderViewModel.earningObservable("4")

                }
            }
        })
        rvTime.adapter=timeSlotAdapter

        orderViewModel.earningObserver.observe(this@TotalEarnActivity, Observer {
            if(it!=null){
                if (it.success){

                    for (del:Pickup in it.data.pickup){
                        try {
                            val allEarningData:AllEarningData= AllEarningData(del.order_reference_no,del.pickup_earn!!)
                            listEarning.add(allEarningData)
                        }catch (e:Exception){
                            e.printStackTrace()
                        }

                    }
                    for (del:Delivery in it.data.delivery){
                        try {
                            val allEarningData:AllEarningData=AllEarningData(del.costume_reference_no,del.delivery_boy_earning!!)
                            listEarning.add(allEarningData)
                        }catch (e:Exception){
                            e.printStackTrace()
                        }

                    }

                    if (!listEarning.isEmpty()){
                        val earningAdapter:EarningAdapter= EarningAdapter(listEarning,this)
                        rvTotalEarn.adapter=earningAdapter;
                    }
                }

            }
        })

    }


}