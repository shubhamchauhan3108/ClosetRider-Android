package com.arramton.closet.rider.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.rider.R
import com.arramton.closet.rider.listener.EditSubChildListener
import com.arramton.closet.rider.model.orderDetails.CostumesOrderItem
import com.arramton.closet.rider.model.orderDetails.OrderItemX

class EditChildOrderDetailsAdapter(val context: Context, val childList:List<CostumesOrderItem>,val editSubChildListener: EditSubChildListener) : RecyclerView.Adapter<EditChildOrderDetailsAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditChildOrderDetailsAdapter.ViewHolder {
      val view :View=LayoutInflater.from(parent.context).inflate(R.layout.custom_order_details_child_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: EditChildOrderDetailsAdapter.ViewHolder, position: Int) {
        holder.childName.text=childList.get(position).name
        holder.childHideName.text=childList.get(position).name
        holder.tvHideQTY.text="QTY : "+childList.get(position).order_item.size.toString()
        holder.tvShowQtY.text="QTY : "+childList.get(position).order_item.size.toString()
        if (childList.get(position).order_item.size < 1) {
            holder.itemView.visibility = View.GONE
        } else {
            holder.itemView.visibility = View.VISIBLE
        }

        holder.rvChildCategory.setHasFixedSize(false)
        val gridLayout= GridLayoutManager(context,2)
        holder.rvChildCategory.layoutManager=gridLayout



        holder.childHideName.setOnClickListener {
            holder.rvChildCategory.visibility=View.GONE
            holder.childHideName.visibility=View.GONE
            holder.childName.visibility=View.VISIBLE
            holder.showLayout.visibility=View.VISIBLE
            holder.hideLayout.visibility=View.GONE
        }
        holder.showLayout.setOnClickListener {

            holder.hideLayout.visibility=View.VISIBLE
            holder.childHideName.visibility=View.VISIBLE
            holder.childName.visibility=View.GONE
            holder.showLayout.visibility=View.GONE
            holder.rvChildCategory.visibility=View.VISIBLE

            val orderDetailsSubChlidAdapter:EditSubChildOrderDetailsAdapter= EditSubChildOrderDetailsAdapter(context,childList.get(position).order_item,object :EditSubChildListener{
                override fun onClickOpenCamera(imgeId: Int) {

                    editSubChildListener.onClickOpenCamera(imgeId)
                }

                override fun onClick(
                    remark: String,
                    costume_id: Int,
                    order_item_id: Int,
                    image: String
                ) {

                    editSubChildListener.onClick(remark,costume_id,order_item_id,image)
                }
            })

            holder.rvChildCategory.adapter=orderDetailsSubChlidAdapter

        }
    }

    override fun getItemCount(): Int {
        return childList.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){

        val childName=view.findViewById<TextView>(R.id.custom_pickup_request_show_btn)
        val childHideName=view.findViewById<TextView>(R.id.custom_pickup_request_hide_btn)
        val rvChildCategory=view.findViewById<RecyclerView>(R.id.custom_pickup_request_rv)
        val hideLayout=view.findViewById<LinearLayout>(R.id.hide_layout)
        val showLayout=view.findViewById<LinearLayout>(R.id.show_layout)
        val tvHideQTY=view.findViewById<TextView>(R.id.hide_qty)
        val tvShowQtY=view.findViewById<TextView>(R.id.show_qty)

    }
}