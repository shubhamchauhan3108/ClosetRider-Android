package com.arramton.closet.rider.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.arramton.closet.rider.localStorage.navigation_header_profile
import com.arramton.closet.rider.repository.AuthRepository
import com.arramton.closet.rider.utils.LoginManager
import com.arramton.closet.rider.viewModel.AuthViewModel
import com.chaos.view.PinView
import com.google.firebase.messaging.FirebaseMessaging

class VerifyOTPActivity : AppCompatActivity() {
    private lateinit var pinView: PinView
    private lateinit var sendOTPBtn:Button
    private lateinit var apiInterface: ApiInterface
    private lateinit var authRepository: AuthRepository
    private lateinit var authViewModel: AuthViewModel
    var mobile:String=""
    private lateinit var loginManager: LoginManager
    private lateinit var tvMsg:TextView
    private lateinit var resendOtp  : TextView

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

        resendOtp = findViewById(R.id.tv_resend_otp)
        mobile=intent.getStringExtra("mobile").toString()
        tvMsg.text="Please check your mobile number "+mobile+" continue to reset your password"
        apiInterface= RetrofitBuilder.getInstance(application)!!.api
        authRepository= AuthRepository(this,apiInterface,application)
        authViewModel= ViewModelProvider(this, AuthFactory(authRepository)).get(AuthViewModel::class.java)

        authViewModel.verifyOTPObservable.observe(this, Observer {
            if (it!=null){
                if (it.success){
                    loginManager.settoken("Bearer "+it.data.token)
                    navigation_header_profile(this@VerifyOTPActivity).saveData(it.data.userInfo.name,it.data.userInfo.mobile_no.toString(),"")
                    startActivity(Intent(this@VerifyOTPActivity,HomePageActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this@VerifyOTPActivity,it.message,Toast.LENGTH_SHORT).show()
                }
            }
        })

        resendOtp.setOnClickListener {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    Toast.makeText(this@VerifyOTPActivity,"Resent OTP",Toast.LENGTH_SHORT).show()
                    authViewModel.loginAuth(mobile.toString(),token)
                    Log.d("TAG", "FCM Token: $token")
                } else {
                    Log.e("TAG", "Fetching FCM token failed: ${task.exception}")
                }
            }
        }

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