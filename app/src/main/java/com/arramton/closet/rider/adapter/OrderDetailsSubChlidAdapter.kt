package com.arramton.closet.rider.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.rider.R
import com.arramton.closet.rider.model.orderDetails.CostumesOrderItem
import com.arramton.closet.rider.model.orderDetails.OrderItemX
import com.bumptech.glide.Glide

class OrderDetailsSubChlidAdapter(val context: Context,val list: List<OrderItemX>) : RecyclerView.Adapter<OrderDetailsSubChlidAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsSubChlidAdapter.ViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.custom_order_details_subchild,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderDetailsSubChlidAdapter.ViewHolder, position: Int) {
        holder.orderName.text=list.get(position).costume.name+" #"+list.get(position).costume_reference_no
        Glide.with(context).load(list.get(position).costume.image_url).into(holder.img)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    class ViewHolder (view : View): RecyclerView.ViewHolder(view){
        val img=view.findViewById<ImageView>(R.id.custom_child_custom_item_img)
        val orderName=view.findViewById<TextView>(R.id.custom_child_custom_item_name)

    }
}