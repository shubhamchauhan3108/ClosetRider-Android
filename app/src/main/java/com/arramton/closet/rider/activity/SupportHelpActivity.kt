package com.arramton.closet.rider.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.arramton.closet.rider.R

class SupportHelpActivity : AppCompatActivity() {
    private lateinit var backBtn : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support_help)

        init()

    }

    private fun init(){

        backBtn = findViewById(R.id.nav_customer_care_back_btn)

        backBtn.setOnClickListener {
            onBackPressed()
        }

    }

}