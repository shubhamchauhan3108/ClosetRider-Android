package com.arramton.closet.rider.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.arramton.closet.rider.R

class AppSettingActivity : AppCompatActivity() {

    private lateinit var backBtn: ImageView
    private lateinit var toolbar_title : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_setting)

        init()

    }

    private fun init(){
        backBtn = findViewById(R.id.nav_customer_care_back_btn)
        toolbar_title = findViewById(R.id.toolbar_title)


        backBtn.setOnClickListener {
            onBackPressed()
        }

        val data = intent.getStringExtra("value")

        if (data.equals("About App")){
            toolbar_title.text = "About App"
        }
        else if(data.equals("Privacy Policy")){
            toolbar_title.text = "Privacy Policy"
        }else{
            toolbar_title.text = "Term and Condition"
        }


    }



}