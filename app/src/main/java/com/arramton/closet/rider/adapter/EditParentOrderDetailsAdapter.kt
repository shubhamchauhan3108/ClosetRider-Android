package com.arramton.closet.rider.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.rider.R
import com.arramton.closet.rider.listener.OrderDetailsListener
import com.arramton.closet.rider.model.orderDetails.OrderItem

class EditParentOrderDetailsAdapter(val context: Context, val list: List<OrderItem>, val childOrderDetails: OrderDetailsListener):RecyclerView.Adapter<EditParentOrderDetailsAdapter.ViewHolder>() {

    var rowIndex=0;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditParentOrderDetailsAdapter.ViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.custom_parent_category_item,parent,false)
        return ViewHolder(view)
    }


    fun updateImage(imgUrl:String,pos: Int){
//        list.get(pos).
    }
    override fun onBindViewHolder(holder: EditParentOrderDetailsAdapter.ViewHolder, position: Int) {
        holder.tvName.text=list.get(position).name
        holder.tvName.setOnClickListener {
            rowIndex=position
            notifyDataSetChanged()
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

    override fun getItemCount(): Int {
      return list.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val tvName=view.findViewById<TextView>(R.id.custom_parent_category_name)
    }
}