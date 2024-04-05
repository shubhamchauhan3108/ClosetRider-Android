package com.arramton.closet.rider.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.factory.ProfileFactory
import com.arramton.closet.rider.repository.ProfileRepository
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.rider.viewModel.ProfileViewModel

class UserProfileActivity : AppCompatActivity() {
    private lateinit var imgBackBtn:ImageView
    private lateinit var apiInterface: ApiInterface
    private lateinit var profileRepository: ProfileRepository
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profileImg : ImageView
    private lateinit var userName:TextView
    private lateinit var userEmail:TextView
    private lateinit var dobLayout: LinearLayout
    private lateinit var name:TextView
    private lateinit var email:TextView
    private lateinit var mobile:TextView
    private lateinit var gender:TextView
    private lateinit var dob:TextView
    private lateinit var address: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        init()
    }

    fun init(){
        imgBackBtn=findViewById(R.id.user_profile_back_btn)

        userEmail=findViewById(R.id.profile_user_email)

        profileImg = findViewById(R.id.profile_image)

        userName=findViewById(R.id.profile_user_name)

        name=findViewById(R.id.profile_name)

        email=findViewById(R.id.profile_email)

        mobile=findViewById(R.id.profile_phone)

        gender=findViewById(R.id.profile_gender)

        dob=findViewById(R.id.profile_dob)

        dobLayout = findViewById(R.id.dob_layout)

        address=findViewById(R.id.profile_address)

        imgBackBtn.setOnClickListener { onBackPressed() }

        apiInterface=RetrofitBuilder.getInstance(application)!!.api

        profileRepository= ProfileRepository(this,apiInterface,application)

        profileViewModel=ViewModelProvider(this,ProfileFactory(profileRepository)).get(ProfileViewModel::class.java)

        profileViewModel.profileResponse.observe(this, Observer {

            if (it!=null){
                if (it.success){
                    userName.text=it.data.name
                    userEmail.text=it.data.email
                    name.text=it.data.name
                    email.text=it.data.email
                    mobile.text=""+it.data.mobile_no
                    gender.text=it.data.gender
                    dobLayout.visibility = View.GONE
                    address.text=it.data.address.address_line_1+", "+it.data.address.landmark+", "+it.data.address.state+", "+it.data.address.city+":"+it.data.address.pincode
                }
            }
        })
        profileViewModel.userProfile()



    }
}