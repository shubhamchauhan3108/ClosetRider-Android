package com.arramton.closet.rider.leftNavigation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arramton.closet.rider.R
import com.arramton.closet.rider.activity.AppSettingActivity
import com.arramton.closet.rider.activity.LoginActivity
import com.arramton.closet.rider.activity.SupportHelpActivity
import com.arramton.closet.rider.activity.UserProfileActivity
import com.arramton.closet.rider.adapter.RightNavigationAdapter
import com.arramton.closet.rider.databinding.ActivityHomePageBinding
import com.arramton.closet.rider.listener.RightNavigationListener
import com.arramton.closet.rider.model.RightNavigationModel
import com.arramton.closet.rider.utils.LoginManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging

class HomePageActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener,
    RightNavigationListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomePageBinding
    private lateinit var navList: ArrayList<RightNavigationModel>
    private lateinit var rightNavigationAdapter: RightNavigationAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var loginManager: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHomePage.toolbar)

         drawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home_page)

        loginManager=LoginManager(this@HomePageActivity)

        binding.appBarHomePage.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.left_navigation)
        navView.setNavigationItemSelectedListener(this)

        navList= ArrayList()
        navList.add(RightNavigationModel(R.drawable.my_profile,"My Profile"));
        navList.add(RightNavigationModel(R.drawable.support_help,"Support & Help"));
        navList.add(RightNavigationModel(R.drawable.about_app,"About App"));
        navList.add(RightNavigationModel(R.drawable.privacy_policy,"Privacy Policy"));
        navList.add(RightNavigationModel(R.drawable.term_condition,"Term and Condition"));
        navList.add(RightNavigationModel(R.drawable.logout,"Log Out"));

        rightNavigationAdapter= RightNavigationAdapter(navList,this@HomePageActivity,this)

        binding.homePageRightNavRv.setHasFixedSize(false)
        binding.homePageRightNavRv.layoutManager= LinearLayoutManager(this@HomePageActivity,
            LinearLayoutManager.VERTICAL,false)
        binding.homePageRightNavRv.adapter=rightNavigationAdapter


        if (intent != null && intent.hasExtra("notificationCall")) {
            val notificationCall = intent.extras?.getString("notificationCall")//.getStringExtra("notificationCall")
            if (notificationCall == "call") {
              openBottomSheet()
            }
        }


        LocalBroadcastManager.getInstance(this).registerReceiver(mHandler, IntentFilter("com.arramton.closet.rider"))
        if (intent.extras!=null){
            for (i in intent?.extras?.keySet()!!){
                if (i.equals("title")){
                    Toast.makeText(this@HomePageActivity, ""+title, Toast.LENGTH_SHORT).show()
                }
            }
        }


        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Get the FCM registration token
                val token = task.result
                // Use the token as needed (e.g., send it to your server)
                Log.d("TAG", "FCM Token: $token")

                // Now you can send this token to your server or perform any other action
            } else {
                // Handle the error gracefully
                Log.e("TAG", "Fetching FCM token failed: ${task.exception}")
            }
        }

        val data = intent?.getStringExtra("data")

        if (data != null) {
            if (data == "1")
                openBottomSheet()
        }




    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_page, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
//            startActivity(Intent(this, Ho::class.java))
            Toast.makeText(this@HomePageActivity,"Notofication clicked",Toast.LENGTH_SHORT).show()

        } else if (item.itemId == android.R.id.home) {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
//            startActivity(Intent(this, Ho::class.java))
            Toast.makeText(this@HomePageActivity,"Notofication clicked",Toast.LENGTH_SHORT).show()

        } else if (item.itemId == android.R.id.home) {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickNavigation(className: String) {
        if (className =="Log Out" ) {
            logoutDialog()
        }


        when(className){

            "My Profile" ->{startActivity(Intent(this,UserProfileActivity::class.java))}
            "Support & Help" ->{startActivity(Intent(this@HomePageActivity, SupportHelpActivity::class.java))}
            "About App" ->{startActivity(Intent(this@HomePageActivity, AppSettingActivity::class.java))}
            "Privacy Policy" ->{startActivity(Intent(this@HomePageActivity,AppSettingActivity::class.java))}
            "Term and Condition" ->{startActivity(Intent(this@HomePageActivity,AppSettingActivity::class.java))}
//            "App Feedback" ->{startActivity(Intent(this@HomePageActivityActivity,ReviewActivity::class.java))}

        }
    }

    fun logoutDialog (){
        val builder = AlertDialog.Builder(this)

        builder.setMessage("Are you sure do you want to logout app ?")
        builder.setTitle("Logout ")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes") {
                dialog, which ->
            loginManager.removeSharedPreference()
            startActivity(Intent(this@HomePageActivity, LoginActivity::class.java))
            finishAffinity()
        }
        builder.setNegativeButton("No") {
                dialog, which -> dialog.cancel()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }


    fun closedApp (){
        val builder = AlertDialog.Builder(this)

        builder.setMessage("Are you sure do you want to close app  ?")
        builder.setTitle("Close App ")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes") {
                dialog, which ->
            finishAffinity()
        }
        builder.setNegativeButton("No") {
                dialog, which -> dialog.cancel()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onBackPressed() {
        closedApp()
    }

    fun openBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.custom_delivery_notification, null)
        dialog.setContentView(view)

        dialog.setCancelable(true)

        dialog.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHandler)
    }

   private val mHandler = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            println("intent = "+intent?.extras)
            Toast.makeText(this@HomePageActivity, "Title is: \n"+intent?.getStringExtra("title"), Toast.LENGTH_SHORT).show()
        }
    }


}