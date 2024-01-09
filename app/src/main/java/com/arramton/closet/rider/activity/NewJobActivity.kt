package com.arramton.closet.rider.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.adapter.DeliveredAdapter
import com.arramton.closet.rider.adapter.NewJobAdapter
import com.arramton.closet.rider.factory.OrderFactory
import com.arramton.closet.rider.listener.NewJobListener
import com.arramton.closet.rider.model.newOrder.Data
import com.arramton.closet.rider.repository.OrderRepository
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.rider.viewModel.OrderViewModel
import com.google.android.material.button.MaterialButton
import org.w3c.dom.Text

class NewJobActivity : AppCompatActivity() {
    private lateinit var pickupBtn:TextView
    private lateinit var deliveryBtn:TextView
    private lateinit var pickupRv:RecyclerView
    private lateinit var deliveryRv:RecyclerView
    private lateinit var viewModel:OrderViewModel
    private lateinit var orderRepository: OrderRepository
    private lateinit var apiInterface: ApiInterface
    private lateinit var newJobAdapter: NewJobAdapter
    private lateinit var nav_customer_care_back_btn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_job)
        init()
    }

    fun init(){

        pickupBtn=findViewById(R.id.new_job_pickup_btn)
        nav_customer_care_back_btn=findViewById(R.id.nav_customer_care_back_btn)
        deliveryBtn=findViewById(R.id.new_job_delivery_btn)
        pickupRv = findViewById(R.id.pickupRv)
        deliveryRv = findViewById(R.id.deliveryRv)
        pickupRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        deliveryRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        apiInterface= RetrofitBuilder.getInstance(application)!!.api
        orderRepository= OrderRepository(apiInterface,this,application)
        viewModel = ViewModelProvider(this, OrderFactory(orderRepository)).get(OrderViewModel::class.java)
        viewModel.newJobs()
        viewModel.newJobLiveData.observe(this, Observer {
            if (it != null) {
                if (it.success) {
                    newJobAdapter = NewJobAdapter(this@NewJobActivity, it.data,object :NewJobListener{
                        override fun onClick(id: String) {
                            startActivity(Intent(this@NewJobActivity,OrderDetailsActivity::class.java).putExtra("id",id))
                        }
                    })
                    pickupRv.adapter = newJobAdapter
                }
                pickupRv.visibility = View.VISIBLE
                deliveryRv.visibility = View.GONE
            }
        })
        viewModel.orderDeliveredLiveData.observe(this) {
            if (it != null) {
                if (it.success) {
                    val adapter = DeliveredAdapter(this@NewJobActivity, it.data)
                    deliveryRv.adapter = adapter
                }
                pickupRv.visibility = View.GONE
                deliveryRv.visibility = View.VISIBLE
            }
        }
        pickupBtn.setOnClickListener {
            deliveryBtn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F2F2F2"))
            deliveryBtn.setTextColor(ContextCompat.getColor(this,R.color.black))
//            deliveryBtn.background = ContextCompat.getDrawable(this, R.drawable.purple_btn)
            pickupBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_purple))
            pickupBtn.setTextColor(ContextCompat.getColor(this,R.color.white))
            viewModel.newJobs()
        }

        deliveryBtn.setOnClickListener {
//            pickupBtn.background = ContextCompat.getDrawable(this, R.drawable.edit_text_bg)
            pickupBtn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F2F2F2"))
            pickupBtn.setTextColor(ContextCompat.getColor(this,R.color.black))
//            deliveryBtn.background = ContextCompat.getDrawable(this, R.drawable.purple_btn)
            deliveryBtn.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_purple))
            deliveryBtn.setTextColor(ContextCompat.getColor(this,R.color.white))
            viewModel.orderDelivered()
        }

        nav_customer_care_back_btn.setOnClickListener{
            onBackPressed()
        }

    }
}