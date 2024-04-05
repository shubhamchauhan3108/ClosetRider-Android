package com.arramton.closet.rider.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.rider.R
import com.arramton.closet.rider.leftNavigation.HomePageActivity
import com.arramton.closet.rider.listener.PickupListener
import com.arramton.closet.rider.model.order.Data
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone


class PickupOrderAdapter(val context: Context,val list: List<Data>,val pickupListener: PickupListener):RecyclerView.Adapter<PickupOrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.custom_pickup_order,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvOrderNumber.text="Order #"+list.get(position).order_reference_no

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
        val date: Date = sdf.parse(list[position].created_at)
        val dateFormat = SimpleDateFormat("MMM dd, yyyy")
        val timeFormat = SimpleDateFormat("hh:mm a")
        dateFormat.timeZone = TimeZone.getDefault()
        timeFormat.timeZone = TimeZone.getDefault()

        val formattedDate: String = dateFormat.format(date)
        val formattedTime: String = timeFormat.format(date)

        holder.timeText.text = "Time: $formattedTime | $formattedDate"

        holder.tvOrderPrice.text="₹ "+list.get(position).pickup_earn

        holder.tvOrderDate.text=""+list.get(position).pickup_date

        holder.tvOrderTime.text=""+list.get(position).pickup_time

        holder.tvOrderAddress.text=""+list.get(position).address.address_line_1+", "+list.get(position).address.landmark+", "+list.get(position).address.state+" , "+list.get(position).address.city+":"+list.get(position).address.pincode

        holder.submitBtn.setOnClickListener {


            pickupListener.submitPickupOrder(list.get(position).id.toString())

        }

        holder.viewDetailsBtn.setOnClickListener {

            pickupListener.onClick(list.get(position).id.toString())

        }

    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){

        val tvOrderNumber=view.findViewById<TextView>(R.id.custom_pickup_order_number)
        val tvOrderPrice=view.findViewById<TextView>(R.id.custom_pickup_order_price)
        val tvOrderDate=view.findViewById<TextView>(R.id.custom_pickup_order_date)
        val tvOrderTime=view.findViewById<TextView>(R.id.custom_pickup_order_time)
        val tvOrderAddress=view.findViewById<TextView>(R.id.custom_pickup_order_address)
        val viewDetailsBtn=view.findViewById<MaterialButton>(R.id.pickup_details_view_details)
        val submitBtn=view.findViewById<MaterialButton>(R.id.custom_pickup_order_submit)
        val timeText = view.findViewById<TextView>(R.id.time_tv)
    }
}