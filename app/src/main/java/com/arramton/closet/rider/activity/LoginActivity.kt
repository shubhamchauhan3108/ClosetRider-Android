package com.arramton.closet.rider.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.factory.AuthFactory
import com.arramton.closet.rider.repository.AuthRepository
import com.arramton.closet.rider.viewModel.AuthViewModel
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var etMobileNumber:EditText
    private lateinit var btnSendOTP:Button
    private lateinit var apiInterface: ApiInterface
    private lateinit var authRepository: AuthRepository
    private lateinit var authViewModel: AuthViewModel
    private lateinit var tvSignUp:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    fun  init(){
        apiInterface=RetrofitBuilder.getInstance(application)!!.api
        authRepository= AuthRepository(this,apiInterface,application)
        authViewModel=ViewModelProvider(this,AuthFactory(authRepository)).get(AuthViewModel::class.java)

        etMobileNumber=findViewById(R.id.login_mobile_number)
        btnSendOTP=findViewById(R.id.mobile_login_next)

        authViewModel.loginObservable.observe(this, Observer {
            if (it!=null){
                if (it.success){
                    startActivity(Intent(this@LoginActivity,VerifyOTPActivity::class.java).putExtra("mobile",etMobileNumber.text.toString()))

                }else{
                    Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                }
            }
        })

        btnSendOTP.setOnClickListener {

            if(etMobileNumber.text.toString().trim().isEmpty()){

                Toast.makeText(this@LoginActivity,"Please Enter Valid Mobile Number",Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            if (!isValid(etMobileNumber.text.toString())){
                Toast.makeText(this@LoginActivity,"Please Enter Valid Mobile Number",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginAuth()
        }

        tvSignUp=findViewById(R.id.login_signup_btn)
        tvSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }


    }

    fun loginAuth(){
        authViewModel.loginAuth(etMobileNumber.text.toString())
    }

    fun isValid(s: String?): Boolean {
        val p = Pattern.compile("^\\d{10}$")
        val m = p.matcher(s)
        return m.matches()
    }
}