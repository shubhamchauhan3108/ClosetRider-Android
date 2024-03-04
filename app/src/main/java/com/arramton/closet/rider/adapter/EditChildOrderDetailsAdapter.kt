package com.arramton.closet.rider.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
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

class EditChildOrderDetailsAdapter(
    val context: Context,
    val childList: List<CostumesOrderItem>,
    val editSubChildListener: EditSubChildListener
) : RecyclerView.Adapter<EditChildOrderDetailsAdapter.ViewHolder>() {

    private lateinit var orderDetailsSubChlidAdapter: EditSubChildOrderDetailsAdapter
    var parentpos = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditChildOrderDetailsAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_order_details_child_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: EditChildOrderDetailsAdapter.ViewHolder,
        @SuppressLint("RecyclerView") Parentposition: Int
    ) {
        holder.childName.text = childList.get(Parentposition).name
        holder.childHideName.text = childList.get(Parentposition).name
        holder.tvHideQTY.text = "QTY : " + childList.get(Parentposition).order_item.size.toString()
        holder.tvShowQtY.text = "QTY : " + childList.get(Parentposition).order_item.size.toString()
        if (childList.get(Parentposition).order_item.size < 1) {
            holder.itemView.visibility = View.GONE
        } else {
            holder.itemView.visibility = View.VISIBLE
        }

        holder.rvChildCategory.setHasFixedSize(false)
        val gridLayout = GridLayoutManager(context, 2)
        holder.rvChildCategory.layoutManager = gridLayout


        holder.childHideName.setOnClickListener {
            holder.rvChildCategory.visibility = View.GONE
            holder.childHideName.visibility = View.GONE
            holder.childName.visibility = View.VISIBLE
            holder.showLayout.visibility = View.VISIBLE
            holder.hideLayout.visibility = View.GONE
        }


        holder.showLayout.setOnClickListener {
            holder.hideLayout.visibility = View.VISIBLE
            holder.childHideName.visibility = View.VISIBLE
            holder.childName.visibility = View.GONE
            holder.showLayout.visibility = View.GONE
            holder.rvChildCategory.visibility = View.VISIBLE

            parentpos = Parentposition

            orderDetailsSubChlidAdapter = EditSubChildOrderDetailsAdapter(
                context,
                childList.get(Parentposition).order_item,
                object : EditSubChildListener {
                    override fun onClickOpenCamera(imgeId: Int, position: Int,parentPosition:Int) {
                        editSubChildListener.onClickOpenCamera(imgeId, position,parentPosition)
                    }

                    override fun onClick(
                        remark: String,
                        costume_id: Int,
                        order_item_id: Int,
                        image: String
                    ) {

                        editSubChildListener.onClick(
                            remark,
                            costume_id,
                            order_item_id,
                            image,
                        )
                    }

                    override fun onClickRemark(id: String) {
                        editSubChildListener.onClickRemark(id)
                    }
                },
                Parentposition)

//            orderDetailsSubChlidAdapter.updateImage(childList.get(position).image_url,position)

            holder.rvChildCategory.adapter = orderDetailsSubChlidAdapter

        }



    }

    fun updateImage(imgUrl: String, pos: Int,parentPosition:Int) {

//        childList.get(pos).image_url=imgUrl
//        notifyItemChanged(pos)

        try {
            orderDetailsSubChlidAdapter.updateImage(imgUrl, pos,parentPosition)
        }
        catch (e:Exception){
            Log.e("null",e.message.toString())
            println(e.message)
        }



    }

    fun childPositionFun(pos: Int) {
        Log.e("null", "Parent position from EditChildOrderDetailAdapter: $parentpos")
        Log.e("null", "Child position from EditChildOrderDetailAdapter: $pos")

    }

    override fun getItemCount(): Int {
        return childList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val childName = view.findViewById<TextView>(R.id.custom_pickup_request_show_btn)
        val childHideName = view.findViewById<TextView>(R.id.custom_pickup_request_hide_btn)
        val rvChildCategory = view.findViewById<RecyclerView>(R.id.custom_pickup_request_rv)
        val hideLayout = view.findViewById<LinearLayout>(R.id.hide_layout)
        val showLayout = view.findViewById<LinearLayout>(R.id.show_layout)
        val tvHideQTY = view.findViewById<TextView>(R.id.hide_qty)
        val tvShowQtY = view.findViewById<TextView>(R.id.show_qty)

    }

}
