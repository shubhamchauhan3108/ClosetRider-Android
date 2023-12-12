package com.arramton.closet.rider.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arramton.closet.rider.R
import com.google.android.material.button.MaterialButton
import kotlin.math.sign

class ChooseAccountActivity : AppCompatActivity() {
    private lateinit var loginBtn:MaterialButton
    private lateinit var signupBtn:MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_account)

        init()
    }



    fun init(){
        loginBtn=findViewById(R.id.choose_account_login_btn)
        signupBtn=findViewById(R.id.choose_account_signup_btn)

        loginBtn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        signupBtn.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))

        }
    }
}