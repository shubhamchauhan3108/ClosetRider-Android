package com.arramton.closet.rider.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.rider.R
import com.arramton.closet.rider.listener.SubmittedListener
import com.arramton.closet.rider.model.order.Data
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone


class SubmittedAdapter(val context: Context, val list: List<Data>, val submittedListener: SubmittedListener) : RecyclerView.Adapter<SubmittedAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.custom_submitted_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
     return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
        val date: Date = sdf.parse(list[position].created_at)
        val dateFormat = SimpleDateFormat("MMM dd, yyyy")
        val timeFormat = SimpleDateFormat("hh:mm a")
        dateFormat.timeZone = TimeZone.getDefault()
        timeFormat.timeZone = TimeZone.getDefault()

        val formattedDate: String = dateFormat.format(date)
        val formattedTime: String = timeFormat.format(date)

        holder.timeText.text = "Time: $formattedTime | $formattedDate"

       holder.tvOrderNo.text="Order #"+list.get(position).id
        holder.tvOrderPrice.text="₹ "+list.get(position).total
        holder.tvPickUpDate.text=""+list.get(position).pickup_date
        holder.tvPickupTime.text=""+list.get(position).pickup_time
        holder.tvAddress.text=""+list.get(position).address.address_line_1+", "+list.get(position).address.landmark+", "+list.get(position).address.state+" , "+list.get(position).address.city+":"+list.get(position).address.pincode

        holder.tvViewDetails.setOnClickListener {
            submittedListener.onClick(list.get(position).id.toString())
        }
    }

    class ViewHolder(view:View) : RecyclerView.ViewHolder(view){
        val tvOrderNo=view.findViewById<TextView>(R.id.custom_submitted_order_no)
        val tvOrderPrice=view.findViewById<TextView>(R.id.custom_submitted_order_price)
        val tvPickUpDate=view.findViewById<TextView>(R.id.custom_submitted_order_date)
        val tvPickupTime=view.findViewById<TextView>(R.id.custom_submitted_order_time)
        val tvAddress=view.findViewById<TextView>(R.id.custom_submitted_order_address)
        val tvViewDetails=view.findViewById<MaterialButton>(R.id.custom_submitted_order_view_details)
        val timeText = view.findViewById<TextView>(R.id.time_tv)

    }

}