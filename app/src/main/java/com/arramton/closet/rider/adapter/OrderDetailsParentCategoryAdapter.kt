package com.arramton.closet.rider.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.rider.R
import com.arramton.closet.rider.listener.OrderDetailsListener
import com.arramton.closet.rider.model.orderDetails.OrderItem


class OrderDetailsParentCategoryAdapter(
    val context: Context,
    val list: List<OrderItem>,
    val childOrderDetails: OrderDetailsListener
) : RecyclerView.Adapter<OrderDetailsParentCategoryAdapter.ViewHolder>() {

    var  rowIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.custom_parent_category_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {




        if (list.get(position).costumes_order_item.size<1){
            holder.tvName.visibility=View.GONE
        }else{
            holder.tvName.visibility=View.VISIBLE


        }
        holder.tvName.text=list.get(position).name
        holder.tvName.setOnClickListener {
            rowIndex=position
            notifyDataSetChanged()
//            notifyItemChanged(position)
        }

        if (rowIndex==position){
            holder.tvName.setBackgroundResource(R.drawable.purple_btn)
            holder.tvName.setTextColor(ContextCompat.getColor(context,R.color.white))
            childOrderDetails.listAdd(list.get(position).costumes_order_item)

        }else{
            holder.tvName.setBackgroundResource(R.drawable.edit_text_bg)
            holder.tvName.setTextColor(ContextCompat.getColor(context,R.color.black))
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val tvName=view.findViewById<TextView>(R.id.custom_parent_category_name)
    }
}