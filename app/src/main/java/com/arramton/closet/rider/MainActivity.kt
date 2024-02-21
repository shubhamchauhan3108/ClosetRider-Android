package com.arramton.closet.rider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.arramton.closet.rider.activity.ChooseAccountActivity
import com.arramton.closet.rider.leftNavigation.HomePageActivity
import com.arramton.closet.rider.utils.LoginManager

class MainActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    private lateinit var loginManager:LoginManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init(){

        loginManager= LoginManager(this)
        handler=Handler()
        handler.postDelayed(Runnable {
            if (loginManager.gettoken()==null || loginManager.gettoken().toString().isEmpty()){
                val intent= Intent(this@MainActivity, ChooseAccountActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                startActivity(Intent(this@MainActivity,HomePageActivity::class.java))
                finish()
            }

        },2000)

    }
}