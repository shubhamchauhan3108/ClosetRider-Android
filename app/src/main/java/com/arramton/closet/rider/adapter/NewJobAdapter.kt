package com.arramton.closet.rider.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.rider.R
import com.arramton.closet.rider.listener.NewJobListener
import com.arramton.closet.rider.model.newOrder.Data
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class NewJobAdapter(val context: Context, val list: List<Data>,val newJobListener: NewJobListener) :RecyclerView.Adapter<NewJobAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view:View=LayoutInflater.from(parent.context).inflate(R.layout.custom_new_job_pickup,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvOrderNo.text="Order #"+list.get(position).order_reference_no
        holder.tvOrderPrice.text="₹ "+list.get(position).pickup_earn
        holder.tvTime.text=list.get(position).pickup_time
        holder.tvDate.text=list.get(position).pickup_date
        holder.tvAddress.text=list.get(position).address.address_line_1+", "+list.get(position).address.landmark+", "+list.get(position).address.state+" , "+list.get(position).address.city+":"+list.get(position).address.pincode

        holder.tvDateTime.text=convertToDate(list.get(position).created_at)

        holder.deliveredBtn.setOnClickListener {
            newJobListener.onClick(list.get(position).id.toString())
        }
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
        val tvOrderNo=view.findViewById<TextView>(R.id.custom_delivered_order_no)
        val tvOrderPrice=view.findViewById<TextView>(R.id.custom_delivered_order_price)
        val tvTime=view.findViewById<TextView>(R.id.custom_delivered_order_time)
        val tvDate=view.findViewById<TextView>(R.id.custom_delivered_order_date)
        val tvAddress=view.findViewById<TextView>(R.id.custom_delivered_order_location)
        val deliveredBtn=view.findViewById<MaterialButton>(R.id.custom_delivered_btn)
        val  tvDateTime=view.findViewById<TextView>(R.id.new_job_pickup_date_time)
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