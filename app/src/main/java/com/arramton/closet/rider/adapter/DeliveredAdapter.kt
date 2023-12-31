package com.arramton.closet.rider.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.rider.R
import com.arramton.closet.rider.model.deliveried.Data
import com.google.android.material.button.MaterialButton

class DeliveredAdapter(val context: Context,val list: List<Data>) :RecyclerView.Adapter<DeliveredAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view:View=LayoutInflater.from(parent.context).inflate(R.layout.custom_delivered_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvOrderNo.text="Order #"+list.get(position).id
        holder.tvOrderPrice.text="₹ "+list.get(position).delivery_boy_earning
        holder.tvTime.text=list.get(position).drop_time
        holder.tvDate.text=list.get(position).drop_date
        holder.tvAddress.text=list.get(position).address.address_line_1+", "+list.get(position).address.landmark+", "+list.get(position).address.state+" , "+list.get(position).address.city+":"+list.get(position).address.pincode
        if (list[position].delivery_order_status_id == 3) {
            holder.deliveredBtn.visibility = View.VISIBLE
            holder.dropBtn.visibility = View.GONE
        } else if (list[position].delivery_order_status_id == 2){
            holder.dropBtn.visibility = View.VISIBLE
            holder.deliveredBtn.visibility = View.GONE
        }

    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
        val tvOrderNo=view.findViewById<TextView>(R.id.custom_delivered_order_no)
        val tvOrderPrice=view.findViewById<TextView>(R.id.custom_delivered_order_price)
        val tvTime=view.findViewById<TextView>(R.id.custom_delivered_order_time)
        val tvDate=view.findViewById<TextView>(R.id.custom_delivered_order_date)
        val tvAddress=view.findViewById<TextView>(R.id.custom_delivered_order_location)
        val deliveredBtn=view.findViewById<MaterialButton>(R.id.custom_delivered_btn)
        val dropBtn=view.findViewById<MaterialButton>(R.id.custom_drop_btn)

    }
}