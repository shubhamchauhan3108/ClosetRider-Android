package com.arramton.closet.rider.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.rider.R
import com.arramton.closet.rider.listener.StateListener
import com.arramton.closet.rider.model.state.State

class StateAdapter(val context: Context,val list: List<State>,val stateListener: StateListener): RecyclerView.Adapter<StateAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.custom_state_list,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.tvStateName.text=list[position].name
        holder.itemView.setOnClickListener {
            stateListener.stateName(list.get(position).id,list.get(position).name)
        }
    }

    class ViewHolder(view : View):RecyclerView.ViewHolder(view){

        val  tvStateName=view.findViewById<TextView>(R.id.custom_state_list_name)

    }
}