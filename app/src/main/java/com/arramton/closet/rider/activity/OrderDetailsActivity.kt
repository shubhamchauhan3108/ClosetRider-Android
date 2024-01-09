package com.arramton.closet.rider.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.adapter.EditParentOrderDetailsAdapter
import com.arramton.closet.rider.adapter.OrderDetailsChildCategoryAdapter
import com.arramton.closet.rider.adapter.OrderDetailsParentCategoryAdapter
import com.arramton.closet.rider.factory.OrderFactory
import com.arramton.closet.rider.listener.OrderDetailsListener
import com.arramton.closet.rider.model.orderDetails.CostumesOrderItem
import com.arramton.closet.rider.repository.OrderRepository
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.rider.viewModel.OrderViewModel
import com.google.android.material.button.MaterialButton
import org.w3c.dom.Text

class OrderDetailsActivity : AppCompatActivity() {
    private lateinit var imgBackBtn:ImageView
    private lateinit var tvOrderNumber:TextView
    private lateinit var tvOrderDate:TextView
    private lateinit var tvOrderTime:TextView
    private lateinit var apiInterface: ApiInterface
    private lateinit var orderRepository: OrderRepository
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var rvCostume:RecyclerView
    private lateinit var rvChildCostume:RecyclerView
    private lateinit var editOrderBtn:MaterialButton

    private lateinit var orderDetailsParentCategoryAdapter: OrderDetailsParentCategoryAdapter
    private lateinit var orderDetailsChildCategoryAdapter: OrderDetailsChildCategoryAdapter

    private lateinit var id:String
    private lateinit var tvSubTotal:TextView
    private lateinit var tvStatus:TextView
    private lateinit var tvMode:TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        init()
    }

    fun init(){

        editOrderBtn=findViewById(R.id.order_details_edit_btn)



        id=intent.getStringExtra("id").toString()

        editOrderBtn.setOnClickListener {

            startActivity(Intent(this@OrderDetailsActivity,EditNewJobActivity::class.java).putExtra("id",id))
        }
        tvSubTotal=findViewById(R.id.order_details_subtotal)
        tvStatus=findViewById(R.id.order_details_status)
        tvMode=findViewById(R.id.order_details_mode)
        imgBackBtn=findViewById(R.id.order_details_back_btn)
        imgBackBtn.setOnClickListener{onBackPressed()}
        tvOrderNumber=findViewById(R.id.order_details_order_number)
        tvOrderDate=findViewById(R.id.order_details_order_date)
        tvOrderTime=findViewById(R.id.order_details_order_time)

        apiInterface=RetrofitBuilder.getInstance(application)!!.api
        orderRepository= OrderRepository(apiInterface,this,application)
        orderViewModel=ViewModelProvider(this,OrderFactory(orderRepository)).get(OrderViewModel::class.java)

        rvCostume=findViewById(R.id.order_details_costume_rv)
        rvCostume.setHasFixedSize(false)
        rvCostume.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)


        rvChildCostume=findViewById(R.id.order_details_costume_child_rv)
        rvChildCostume.setHasFixedSize(false)
        rvChildCostume.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)


        rvChildCostume=findViewById(R.id.order_details_costume_child_rv)
        rvCostume=findViewById(R.id.order_details_costume_rv)
        rvChildCostume=findViewById(R.id.order_details_costume_child_rv)

        orderViewModel.orderDetailsLiveData.observe(this, Observer {

            if (it!=null){
                if(it.success){
                    tvOrderNumber.text="Order #"+it.data.order.id
                    tvOrderDate.text=it.data.order.pickup_date
                    tvOrderTime.text=it.data.order.pickup_time
                    tvSubTotal.text="₹ "+it.data.order?.sub_total
                    tvStatus.text=""+it.data.order?.payment_status
                    tvMode.text=""+it.data.order?.payment_mode

                    orderDetailsParentCategoryAdapter= OrderDetailsParentCategoryAdapter(this,it.data.orderItem,object :OrderDetailsListener{
                        override fun listAdd(list1: List<CostumesOrderItem>) {

                             orderDetailsChildCategoryAdapter=OrderDetailsChildCategoryAdapter(this@OrderDetailsActivity,list1)
                            rvChildCostume.adapter=orderDetailsChildCategoryAdapter

                        }
                    })
                    rvCostume.adapter=orderDetailsParentCategoryAdapter


                }
            }
        })
        orderViewModel.orderDetails(id)


    }
}