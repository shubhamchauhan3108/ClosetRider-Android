package com.arramton.closet.rider.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.factory.AuthFactory
import com.arramton.closet.rider.repository.AuthRepository
import com.arramton.closet.rider.viewModel.AuthViewModel
import com.google.firebase.messaging.FirebaseMessaging
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var etMobileNumber:EditText
    private lateinit var btnSendOTP:Button
    private lateinit var apiInterface: ApiInterface
    private lateinit var authRepository: AuthRepository
    private lateinit var authViewModel: AuthViewModel
    private lateinit var tvSignUp:TextView

    companion object {
        private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    fun  init(){

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is already granted
            // You can proceed with your notification related tasks
        } else {
            // Permission is not yet granted, request it
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
            }
        }




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
            else if (!isValid(etMobileNumber.text.toString())){
                Toast.makeText(this@LoginActivity,"Please Enter Valid Mobile Number",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else

                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val token = task.result
                        loginAuth(token)
                        Log.d("TAG", "FCM Token: $token")
                    } else {
                        Log.e("TAG", "Fetching FCM token failed: ${task.exception}")
                    }
                }


        }
        tvSignUp=findViewById(R.id.login_signup_btn)
        tvSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }


    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted

            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
            }
        }

    }


    fun loginAuth(token: String){
        authViewModel.loginAuth(etMobileNumber.text.toString(),token)
    }

    fun isValid(s: String?): Boolean {
        val p = Pattern.compile("^\\d{10}$")
        val m = p.matcher(s)
        return m.matches()
    }
}