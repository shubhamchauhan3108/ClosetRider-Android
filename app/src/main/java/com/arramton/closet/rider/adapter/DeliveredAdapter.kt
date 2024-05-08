package com.arramton.closet.rider.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.rider.R
import com.arramton.closet.rider.listener.DeliveryListener
import com.arramton.closet.rider.model.deliveried.Data
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class DeliveredAdapter(val context: Context,val list: List<Data>,val deliveryListener: DeliveryListener) :RecyclerView.Adapter<DeliveredAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view:View=LayoutInflater.from(parent.context).inflate(R.layout.custom_delivered_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvOrderNo.text="Order #"+list.get(position).order.order_reference_no
        holder.tvOrderPrice.text="₹ "+list.get(position).delivery_boy_earning
        holder.tvTime.text=list.get(position).order.drop_time
        holder.tvDate.text=list.get(position).order.drop_date
        holder.tvDateTime.text=convertToDate(list.get(position).order.created_at)
        holder.tvAddress.text=list.get(position).order.address.address_line_1+", "+list.get(position).order.address.landmark+", "+list.get(position).order.address.state+" , "+list.get(position).order.address.city+":"+list.get(position).order.address.pincode
        if (list[position].delivery_order_status_id == 3) {
            holder.deliveredBtn.visibility = View.VISIBLE
            holder.dropBtn.visibility = View.GONE
        } else if (list[position].delivery_order_status_id == 2){
            holder.dropBtn.visibility = View.VISIBLE
            holder.deliveredBtn.visibility = View.GONE
        } else {
            holder.dropBtn.visibility = View.VISIBLE
            holder.deliveredBtn.visibility = View.GONE
        }
        holder.dropBtn.setOnClickListener {
            deliveryListener.onClick(list.get(position).id)
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
        val tvDateTime=view.findViewById<TextView>(R.id.custom_delivered_item_time)


    }

    private fun convertToDate(dateString: String): String? {

        var date: Date? = null

        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)

        df.timeZone = TimeZone.getTimeZone("UTC")

        try {

            date = df.parse(dateString)

        } catch (e: Exception) {

            e.printStackTrace()

        }

        df.timeZone = TimeZone.getDefault()

        val outputFormat = SimpleDateFormat("hh:mm:ss aa | "+"dd MMM yyyy", Locale.getDefault())

        return outputFormat.format(date)
    }


}