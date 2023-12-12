package com.arramton.closet.rider.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.rider.R
import com.arramton.closet.rider.listener.TimeSlotListener
import com.arramton.closet.rider.model.TimeSlotModel

class TimeSlotAdapter(val context: Context,val list: List<TimeSlotModel>,val timeSlotListener: TimeSlotListener) : RecyclerView.Adapter<TimeSlotAdapter.ViewHolder>() {

    var rowIndex:Int=0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.custom_time_slot,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.tvTimeName.text=list.get(position).timeName
        holder.tvTimeName.setOnClickListener {
            rowIndex=position
            notifyDataSetChanged()
        }


        if (rowIndex==position){
            holder.tvTimeName.setBackgroundResource(R.drawable.purple_btn)
            holder.tvTimeName.setTextColor(ContextCompat.getColor(context,R.color.white))
            timeSlotListener.onClickTime(list.get(position).timeName)
        }else{
            holder.tvTimeName.setBackgroundResource(R.drawable.edit_text_bg)
            holder.tvTimeName.setTextColor(ContextCompat.getColor(context,R.color.black))
        }
    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view){

        val tvTimeName=view.findViewById<TextView>(R.id.custom_time_slot_btn_name)


    }

}