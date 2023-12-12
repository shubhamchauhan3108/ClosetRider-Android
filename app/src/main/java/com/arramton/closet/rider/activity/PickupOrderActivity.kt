package com.arramton.closet.rider.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.arramton.closet.rider.R

class PickupOrderActivity : AppCompatActivity() {
    private lateinit var backBtn:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pickup_order)
    }

    fun init(){
        backBtn=findViewById(R.id.pickup_order_back_btn)
        backBtn.setOnClickListener{onBackPressed()}
    }
}