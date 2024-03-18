package com.arramton.closet.rider.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.adapter.PickupOrderAdapter
import com.arramton.closet.rider.factory.OrderFactory
import com.arramton.closet.rider.leftNavigation.HomePageActivity
import com.arramton.closet.rider.listener.PickupListener
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
                    pickupOrderAdapter= PickupOrderAdapter(this,it.data,object:PickupListener{
                        override fun onClick(id: String) {
                            startActivity(Intent(this@PickupOrderActivity,OrderDetailsActivity::class.java).putExtra("id",id).putExtra("key","pickupJob"))
                        }
                        override fun submitPickupOrder(id: String) {
                            pickupOrderSubmit(id)
                        }
                    })
                    rvOrderPickup.adapter=pickupOrderAdapter

                }
            }
        })
        orderViewModel.pickupOrder()

        orderViewModel.submitPickupOrderObserver.observe(this, Observer {
            if (it!=null){
                Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        })

    }

    fun pickupOrderSubmit(id:String){
        val builder = AlertDialog.Builder(this)

        builder.setMessage("Do you want to Submit ")
        builder.setTitle("Closet Rider")

        builder.setCancelable(false)

        builder.setPositiveButton("Yes") {
                dialog, which -> finish()

            orderViewModel.submitPickupOrderObservable(id)
          startActivity(Intent(this@PickupOrderActivity, HomePageActivity::class.java))
            dialog.dismiss()
        }

        builder.setNegativeButton("No") {
                dialog, which -> dialog.cancel()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

}