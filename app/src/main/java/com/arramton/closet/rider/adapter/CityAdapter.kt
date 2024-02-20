package com.arramton.closet.rider.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.rider.R
import com.arramton.closet.rider.listener.StateListener
import com.arramton.closet.rider.model.state.City
import com.arramton.closet.rider.model.state.State

class CityAdapter(val context: Context, val list: List<City>, val stateListener: StateListener):RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.ViewHolder {
        val view:View= LayoutInflater.from(parent.context).inflate(R.layout.custom_state_list,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityAdapter.ViewHolder, position: Int) {
        holder.tvStateName.text=list[position].name
        holder.itemView.setOnClickListener {
            stateListener.stateName(list.get(position).id,list.get(position).name)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view :View) : RecyclerView.ViewHolder(view){
        val  tvStateName=view.findViewById<TextView>(R.id.custom_state_list_name)

    }
}