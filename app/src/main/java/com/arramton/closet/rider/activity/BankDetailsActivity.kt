package com.arramton.closet.rider.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.arramton.closet.rider.R
import com.google.android.material.button.MaterialButton

class BankDetailsActivity : AppCompatActivity() {
    private lateinit var name:String
    private lateinit var mobile:String
    private lateinit var email:String
    private lateinit var gender:String
    private lateinit var address:String
    private lateinit var landMark:String
    private lateinit var state:String
    private lateinit var city:String
    private lateinit var pincode:String
    private lateinit var etBankName:EditText
    private lateinit var etAccountHolderName:EditText
    private lateinit var etAccountNumber:EditText
    private lateinit var etIFSCCode:EditText
    private lateinit var adharNumber:String
    private lateinit var panNumber:String
    private lateinit var front:String
    private lateinit var back:String
    private lateinit var pancard:String
    private lateinit var bankDetailsNextBtn:MaterialButton
    private lateinit var bankDetailsBackBtn:MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_details)
        init()
    }

    fun init(){

        name=intent.getStringExtra("name").toString()

        mobile=intent.getStringExtra("mobile").toString()

        email=intent.getStringExtra("email").toString()

        gender=intent.getStringExtra("gender").toString()

        address=intent.getStringExtra("address").toString()

        landMark=intent.getStringExtra("landMark").toString()

        state=intent.getStringExtra("state").toString()

        city=intent.getStringExtra("city").toString()

        pincode=intent.getStringExtra("pincode").toString()

        front=intent.getStringExtra("front").toString()

        back=intent.getStringExtra("back").toString()

        pancard=intent.getStringExtra("pancard").toString()

        adharNumber=intent.getStringExtra("addharNumber").toString()

        panNumber=intent.getStringExtra("pancardNumber").toString()

        etBankName=findViewById(R.id.bank_details_bank_name)

        etAccountHolderName=findViewById(R.id.bank_details_bank_holder_name)

        etAccountNumber=findViewById(R.id.bank_details_account_number)

        etIFSCCode=findViewById(R.id.bank_details_ifsc_code)

        bankDetailsNextBtn=findViewById(R.id.bank_details_register_btn)

        bankDetailsBackBtn=findViewById(R.id.bank_details_back_btn)

        bankDetailsNextBtn.setOnClickListener {
            val intent:Intent=Intent(this@BankDetailsActivity,UploadProfilePhotoActivity::class.java)
            intent.putExtra("name",name)
            intent.putExtra("mobile",mobile)
            intent.putExtra("email",email)
            intent.putExtra("gender",gender)
            intent.putExtra("address",address)
            intent.putExtra("landMark",landMark)
            intent.putExtra("state",state)
            intent.putExtra("city",city)
            intent.putExtra("pincode",pincode)
            intent.putExtra("front",front)
            intent.putExtra("back",back)
            intent.putExtra("pancard",pancard)
            intent.putExtra("addharNumber",adharNumber)
            intent.putExtra("pancardNumber",panNumber)
            intent.putExtra("bankName",etBankName.text.toString())
            intent.putExtra("holderName",etAccountHolderName.text.toString())
            intent.putExtra("accountNumber",etAccountNumber.text.toString())
            intent.putExtra("ifscCode",etIFSCCode.text.toString())
            startActivity(intent)
        }

        bankDetailsBackBtn.setOnClickListener {
            onBackPressed()
        }



    }
}