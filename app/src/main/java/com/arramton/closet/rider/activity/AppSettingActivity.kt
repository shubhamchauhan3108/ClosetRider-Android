package com.arramton.closet.rider.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.factory.ProfileFactory
import com.arramton.closet.rider.repository.ProfileRepository
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.rider.viewModel.ProfileViewModel

class AppSettingActivity : AppCompatActivity() {

    private lateinit var backBtn: ImageView
    private lateinit var toolbar_title : TextView

    private lateinit var imgBckBtn:ImageView
    private lateinit var url:String
    private lateinit var tvMsg:TextView

    private lateinit var apiInterface: ApiInterface
    private lateinit var profileRepository: ProfileRepository
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_setting)

        init()

    }

    private fun init(){
        toolbar_title = findViewById(R.id.toolbar_title)
        url=intent.getStringExtra("url").toString()
        title=intent.getStringExtra("title").toString()
        imgBckBtn=findViewById(R.id.app_setting_back_btn)
        tvMsg=findViewById(R.id.app_setting_msg)
        apiInterface= RetrofitBuilder.getInstance(application)!!.api

        profileRepository= ProfileRepository(this,apiInterface,application)

        profileViewModel= ViewModelProvider(this, ProfileFactory(profileRepository)).get(ProfileViewModel::class.java)


        imgBckBtn.setOnClickListener {
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


        profileViewModel.appSettingObservable.observe(this, Observer {
            if (it!=null){
                if (it.success){
                    tvMsg.text= Html.fromHtml(it.data)
                }
            }
        })

        profileViewModel.appSettingObserver(url)

    }



}