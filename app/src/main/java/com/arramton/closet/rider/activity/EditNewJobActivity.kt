package com.arramton.closet.rider.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.arramton.closet.rider.R

class EditNewJobActivity : AppCompatActivity() {
    private lateinit var imgBackBtn:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_new_job)
        init()
    }

    fun init(){

        imgBackBtn=findViewById(R.id.edit_new_job_back_btn)
        imgBackBtn.setOnClickListener {
            onBackPressed()
        }

    }
}