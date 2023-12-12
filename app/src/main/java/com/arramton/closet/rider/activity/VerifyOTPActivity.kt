package com.arramton.closet.rider.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.factory.AuthFactory
import com.arramton.closet.rider.leftNavigation.HomePageActivity
import com.arramton.closet.rider.repository.AuthRepository
import com.arramton.closet.rider.utils.LoginManager
import com.arramton.closet.rider.viewModel.AuthViewModel
import com.chaos.view.PinView

class VerifyOTPActivity : AppCompatActivity() {
    private lateinit var pinView: PinView
    private lateinit var sendOTPBtn:Button
    private lateinit var apiInterface: ApiInterface
    private lateinit var authRepository: AuthRepository
    private lateinit var authViewModel: AuthViewModel
    var mobile:String=""
    private lateinit var loginManager: LoginManager
    private lateinit var tvMsg:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otpactivity)
        init()
    }

    fun init(){
        pinView=findViewById(R.id.firstPinView)
        sendOTPBtn=findViewById(R.id.verify_otp_btn)
        tvMsg=findViewById(R.id.verify_otp_msg)

        loginManager= LoginManager(this)

        mobile=intent.getStringExtra("mobile").toString()
        tvMsg.text="Please check your mobile number "+mobile+" continue to reset your password"
        apiInterface= RetrofitBuilder.getInstance(application)!!.api
        authRepository= AuthRepository(this,apiInterface,application)
        authViewModel= ViewModelProvider(this, AuthFactory(authRepository)).get(AuthViewModel::class.java)

        authViewModel.verifyOTPObservable.observe(this, Observer {
            if (it!=null){
                if (it.success){
                    loginManager.settoken("Bearer "+it.data.token)
                    startActivity(Intent(this@VerifyOTPActivity,HomePageActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this@VerifyOTPActivity,it.message,Toast.LENGTH_SHORT).show()
                }
            }
        })

        sendOTPBtn.setOnClickListener {
            if (pinView.text.toString().trim().isEmpty()){
                Toast.makeText(this@VerifyOTPActivity,"Please Enter Valid OTP",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            verifyOTP(pinView.text.toString())



        }
    }

    fun verifyOTP(otp:String){
        authViewModel.verifyOTP(mobile,otp)
    }


}