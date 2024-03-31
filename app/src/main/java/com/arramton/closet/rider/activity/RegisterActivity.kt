package com.arramton.closet.rider.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.restService.RetrofitBuilder
import com.arramton.closet.rider.R
import com.arramton.closet.rider.adapter.CityAdapter
import com.arramton.closet.rider.adapter.StateAdapter
import com.arramton.closet.rider.factory.AuthFactory
import com.arramton.closet.rider.leftNavigation.HomePageActivity
import com.arramton.closet.rider.listener.StateListener
import com.arramton.closet.rider.model.state.City
import com.arramton.closet.rider.model.state.State
import com.arramton.closet.rider.model.state.StateModelResponse
import com.arramton.closet.rider.repository.AuthRepository
import com.arramton.closet.rider.restService.ApiInterface
import com.arramton.closet.rider.viewModel.AuthViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.file.Path

class RegisterActivity : AppCompatActivity() {

    private lateinit var apiInterface: ApiInterface
    private lateinit var authRepository: AuthRepository
    private lateinit var authViewModel: AuthViewModel
    private var mobileNumber: String? = null

    private lateinit var registerMaterialBtn: MaterialButton
    private lateinit var etName: EditText
    private lateinit var etMobile: EditText
    private lateinit var etEmail: EditText
    private lateinit var etGender: EditText
    private lateinit var etAddress: EditText
    private lateinit var etLandMark: EditText
    private lateinit var etPinCode: EditText

    private lateinit var loginText: TextView
    private lateinit var tvState: TextView
    private lateinit var tvCity: TextView
    private var stateId: Int = 0
    private var cityId: Int = 0
    private lateinit var stateList: List<State>;
    private lateinit var cityList: List<City>;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    fun init() {
        registerMaterialBtn = findViewById(R.id.register_btn)

        stateList = listOf();

        etName = findViewById(R.id.register_name);

        etMobile = findViewById(R.id.register_mobile)

        etEmail = findViewById(R.id.register_email)

        etGender = findViewById(R.id.register_gender)

        etAddress = findViewById(R.id.register_address)

        etLandMark = findViewById(R.id.register_landmark)

        etPinCode = findViewById(R.id.register_pincode)

        loginText = findViewById(R.id.login_btn)

        loginText.setOnClickListener {
            onBackPressed()
//            startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
//            finish()
        }

        apiInterface = RetrofitBuilder.getInstance(application)!!.api
        authRepository = AuthRepository(this, apiInterface, application)
        authViewModel =
            ViewModelProvider(this, AuthFactory(authRepository)).get(AuthViewModel::class.java)

        authViewModel.verifyOTPObservable.observe(this, Observer {
            if (it != null) {
                mobileNumber = it.data.userInfo.mobile_no.toString()
            }
        })


        registerMaterialBtn.setOnClickListener {

            if (etName.text.toString().trim().isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Enter Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (etMobile.text.toString().trim().isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Enter Mobile Number", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else if (etEmail.text.toString().trim().isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Enter Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (etGender.text.toString().trim().isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Enter Gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (etAddress.text.toString().trim().isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Enter Address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (etLandMark.text.toString().trim().isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Enter Landmark", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (stateId == 0) {
                Toast.makeText(this@RegisterActivity, "Please Select State ", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else if (cityId == 0) {
                Toast.makeText(this@RegisterActivity, "Please Select City ", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else if (etPinCode.text.toString().trim().isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Enter Pincode", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                if (etMobile.text.toString().length < 10 || etMobile.text.toString().length > 10) {

                    Toast.makeText(
                        this@RegisterActivity,
                        "Mobile number should be equal to 10",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }else {
                    val intent =
                        Intent(this@RegisterActivity, UploadDocumentsActivity::class.java)
                    intent.putExtra("name", etName.text.toString())
                    intent.putExtra("mobile", etMobile.text.toString())
                    intent.putExtra("email", etEmail.text.toString())
                    intent.putExtra("gender", etGender.text.toString())
                    intent.putExtra("address", etAddress.text.toString())
                    intent.putExtra("landMark", etLandMark.text.toString())
                    intent.putExtra("state", tvState.text.toString())
                    intent.putExtra("city", tvCity.text.toString())
                    intent.putExtra("pincode", etPinCode.text.toString())
                    startActivity(intent)
                }
            }

        }

        tvState = findViewById(R.id.register_state_name)
        tvCity = findViewById(R.id.register_city_name)

        tvState.setOnClickListener {

            showState()

        }

        tvCity.setOnClickListener {

            if (stateId == 0) {
                Toast.makeText(this@RegisterActivity, "Please Select State ", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else {
                showCity(stateId)

            }
        }

    }

    fun showState() {

        println("abc" + readJsonFile(this@RegisterActivity, "india_states.json"))

        val dialog = BottomSheetDialog(this@RegisterActivity)

        val view = layoutInflater.inflate(R.layout.show_state_name, null)

        val btnName = view.findViewById<TextView>(R.id.state_bottom_sheet_name)

        btnName.text = "State Name"

        val rvState = view.findViewById<RecyclerView>(R.id.show_state_bottom_sheet_rv)

        rvState.setHasFixedSize(false)

        rvState.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val gson = Gson()
        val state: StateModelResponse = gson.fromJson(
            readJsonFile(this@RegisterActivity, "india_states.json"),
            StateModelResponse::class.java
        )

        stateList = state.states

        val stateAdapter: StateAdapter =
            StateAdapter(this@RegisterActivity, stateList, object : StateListener {
                override fun stateName(id: Int, name: String) {
                    tvState.text = name
                    stateId = id
                    cityId = 0
                    tvCity.text =  "Select City"
                    dialog.dismiss()
                }
            })

        rvState.adapter = stateAdapter

        dialog.setCancelable(true)

        dialog.setContentView(view)

        dialog.show()

    }

    fun showCity(id: Int) {

        val gson = Gson()

//        val city:StateModelResponse = gson.fromJson(readJsonFile(this@RegisterActivity,"india_states.json"), StateModelResponse::class.java)
        for (i in 0..stateList.size - 1) {

            if (id == stateList[i].id) {
                cityList = stateList[i].cities
            }


        }
        val dialog = BottomSheetDialog(this@RegisterActivity)

        val view = layoutInflater.inflate(R.layout.show_state_name, null)

        val btnName = view.findViewById<TextView>(R.id.state_bottom_sheet_name)

        btnName.text = "City Name"

        val rvState = view.findViewById<RecyclerView>(R.id.show_state_bottom_sheet_rv)



        rvState.setHasFixedSize(false)

        rvState.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val cityAdapter: CityAdapter =
            CityAdapter(this@RegisterActivity, cityList, object : StateListener {
                override fun stateName(id: Int, name: String) {
                    tvCity.text = name
                    cityId = id
                    dialog.dismiss()
                }
            })

        rvState.adapter = cityAdapter

        dialog.setCancelable(true)

        dialog.setContentView(view)

        dialog.show()
    }

    fun readJsonFile(context: Context, path: String): String {
        try {
            val file = context.assets.open("$path")
            val bufferedReader = BufferedReader(InputStreamReader(file))
            val stringBuilder = StringBuilder()
            bufferedReader.useLines { lines ->
                lines.forEach {
                    stringBuilder.append(it)
                }
            }
            val jsonString = stringBuilder.toString()
            println("jbhvh" + jsonString)
            return jsonString
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }
}