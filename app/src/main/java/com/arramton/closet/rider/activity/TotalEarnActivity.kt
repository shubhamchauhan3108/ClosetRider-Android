package com.arramton.closet.rider.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.rider.R
import com.arramton.closet.rider.adapter.TimeSlotAdapter
import com.arramton.closet.rider.listener.TimeSlotListener
import com.arramton.closet.rider.model.TimeSlotModel

class TotalEarnActivity : AppCompatActivity() {
    private lateinit var rvTotalEarn:RecyclerView
    private lateinit var listTime: ArrayList<TimeSlotModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_earn)
        init()
    }

    fun init(){
        listTime= arrayListOf()
        rvTotalEarn=findViewById(R.id.total_earn_rv)
        rvTotalEarn.setHasFixedSize(false)
        rvTotalEarn.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        listTime.add(TimeSlotModel("Today"))
        listTime.add(TimeSlotModel("Weekly"))
        listTime.add(TimeSlotModel("Monthly"))
        listTime.add(TimeSlotModel("Yearly"))

        val timeSlotAdapter:TimeSlotAdapter=TimeSlotAdapter(this@TotalEarnActivity,listTime,object :TimeSlotListener{
            override fun onClickTime(name: String) {
               //calling api

            }
        })
        rvTotalEarn.adapter=timeSlotAdapter
    }
}