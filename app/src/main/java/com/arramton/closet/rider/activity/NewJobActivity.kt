package com.arramton.closet.rider.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.adapter.DeliveredAdapter
import com.arramton.closet.rider.adapter.NewJobAdapter
import com.arramton.closet.rider.factory.AuthFactory
import com.arramton.closet.rider.factory.OrderFactory
import com.arramton.closet.rider.listener.DeliveryListener
import com.arramton.closet.rider.listener.NewJobListener
import com.arramton.closet.rider.model.newOrder.Data
import com.arramton.closet.rider.repository.AuthRepository
import com.arramton.closet.rider.repository.OrderRepository
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.rider.viewModel.AuthViewModel
import com.arramton.closet.rider.viewModel.OrderViewModel
import com.chaos.view.PinView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import org.w3c.dom.Text
import java.util.Objects

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
    private lateinit var authViewModel: AuthViewModel
    private lateinit var authRepository: AuthRepository
    private var pickId:Int=0

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
        authRepository= AuthRepository(this,apiInterface,application)
        authViewModel= ViewModelProvider(this, AuthFactory(authRepository)).get(AuthViewModel::class.java)
        viewModel.newJobs()

        authViewModel.pickupOrderLoginObservable.observe(this,{
            if (it!=null){
                if (it.success){
                    Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                    verifyOTPBottomSheet()

                }
            }
        })



        viewModel.newJobLiveData.observe(this, Observer {
            if (it != null) {
                if (it.success) {
                    newJobAdapter = NewJobAdapter(this@NewJobActivity, it.data,object :NewJobListener{
                        override fun onClick(id: String) {
                            startActivity(Intent(this@NewJobActivity,OrderDetailsActivity::class.java).putExtra("id",id).putExtra("key","newJob"))
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
                    val adapter = DeliveredAdapter(this@NewJobActivity, it.data,object : DeliveryListener{
                        override fun onClick(id: Int) {
                            authViewModel.pickupOrderLoginAuth(id.toString())
//                            verifyOTPBottomSheet(id)dialog = BottomSheetDialog(this)
                            pickId=id;


                        }
                    })
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
            viewModel.orderDelivered("1")
        }

        nav_customer_care_back_btn.setOnClickListener{
            onBackPressed()
        }

    }

    fun verifyOTPBottomSheet(){
        var dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.custom_pickup_verify_otp, null)
        val otp=view.findViewById<PinView>(R.id.pickup_verify_otp)
        val btnVerifyOTP=view.findViewById<Button>(R.id.pickup_verify_otp_btn)

        authViewModel.pickupOrderVerifyOTPObservable.observe(this, Observer {
            if (it!=null){
                if(it.success){
                    viewModel.orderDelivered("1")
                    dialog.dismiss()

                }
            }
        })


        btnVerifyOTP.setOnClickListener {
            if (otp.text.toString().trim().isEmpty()){
                Toast.makeText(this@NewJobActivity,"Please Enter OTP",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickId==0){
                Toast.makeText(this@NewJobActivity,"In-valid Order Id",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            authViewModel.pickupOrderVerifyOTP(pickId.toString(),otp.text.toString())
        }

        dialog.setCancelable(true)

        dialog.setContentView(view)

        dialog.show()


    }
}