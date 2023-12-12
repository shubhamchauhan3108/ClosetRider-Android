package com.arramton.closet.rider.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.arramton.closet.rider.R
import com.google.android.material.button.MaterialButton
import org.w3c.dom.Text

class NewJobActivity : AppCompatActivity() {
    private lateinit var pickupBtn:TextView
    private lateinit var deliveryBtn:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_job)
        init()
    }

    fun init(){
        pickupBtn=findViewById(R.id.new_job_pickup_btn)
        deliveryBtn=findViewById(R.id.new_job_delivery_btn)

        pickupBtn.setOnClickListener {
            pickupBtn.setBackgroundResource(R.drawable.purple_btn)
            pickupBtn.setTextColor(ContextCompat.getColor(this,R.color.white))

            deliveryBtn.setBackgroundResource(R.drawable.edit_text_bg)
            deliveryBtn.setTextColor(ContextCompat.getColor(this,R.color.black))

        }


        deliveryBtn.setOnClickListener {
            pickupBtn.setBackgroundResource(R.drawable.edit_text_bg)
            pickupBtn.setTextColor(ContextCompat.getColor(this,R.color.black))

            deliveryBtn.setBackgroundResource(R.drawable.purple_btn)
            deliveryBtn.setTextColor(ContextCompat.getColor(this,R.color.white))
        }

    }
}