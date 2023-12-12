package com.arramton.closet.rider.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.rider.R
import com.google.android.material.button.MaterialButton

class WeeklyAdapter(val context: Context) : RecyclerView.Adapter<WeeklyAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyAdapter.ViewHolder {

        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.custom_week_name,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeeklyAdapter.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {

        return 2;
    }

    class ViewHolder(view : View) :RecyclerView.ViewHolder(view){
        var name:MaterialButton=view.findViewById(R.id.weekly_name)
    }
}