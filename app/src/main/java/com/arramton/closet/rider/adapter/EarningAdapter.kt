package com.arramton.closet.rider.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.rider.R
import com.arramton.closet.rider.model.earning.AllEarningData

class EarningAdapter(val listEarning:List<AllEarningData>,val context: Context) :RecyclerView.Adapter<EarningAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view :View=LayoutInflater.from(parent.context).inflate(R.layout.custom_transaction_details_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listEarning.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvOrderNumber.text=listEarning.get(position).orderId
        holder.tvOrderAmt.text="₹ "+listEarning.get(position).orderAmt
    }

    class ViewHolder(item:View) : RecyclerView.ViewHolder(item) {
        val tvOrderNumber=item.findViewById<TextView>(R.id.custom_transaction_details_item_order_number)
        val tvOrderAmt=item.findViewById<TextView>(R.id.custom_transaction_details_item_order_amt)
    }

}