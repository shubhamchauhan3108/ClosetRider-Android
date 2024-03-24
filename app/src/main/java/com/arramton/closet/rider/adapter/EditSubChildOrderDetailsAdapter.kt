package com.arramton.closet.rider.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.arramton.closet.rider.R
import com.arramton.closet.rider.listener.EditSubChildListener
import com.arramton.closet.rider.model.newOrder.editNewOrder.Item
import com.arramton.closet.rider.model.orderDetails.OrderItemX
import com.bumptech.glide.Glide

class EditSubChildOrderDetailsAdapter(val context: Context, val list: List<OrderItemX>,val editSubChildListener: EditSubChildListener,val Parentposition : Int): RecyclerView.Adapter<EditSubChildOrderDetailsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View= LayoutInflater.from(parent.context).inflate(R.layout.custom_sub_child_order_details_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return list.size
    }

        fun updateImage(imgUrl: String, pos: Int, parentPosition: Int) {
            if (parentPosition == Parentposition) {
                list.get(pos).updateImge = imgUrl
                notifyItemChanged(pos)
            } else {
                Toast.makeText(
                    context,
                    "position not match\n updateImage position: $parentPosition\nAdapter Constructor position: $Parentposition",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(
                    "null", "position not match\n" +
                            " updateImage position: $parentPosition\n" +
                            "Adapter Constructor position: $Parentposition"
                )
            }

        }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        if (list.get(position).updateImge==null){
            Glide.with(context).load(R.drawable.baseline_photo_camera_24).into(holder.img)
        }else{
            Glide.with(context).load(list.get(position).updateImge).into(holder.img)
        }

        holder.cameraOpen.setOnClickListener {
            Log.e("null","Child position from EditSubChildOrderDetailAdapter: $position")
            Log.e("null","Parent position from EditSubChildOrderDetailAdapter: $Parentposition")
            editSubChildListener.onClickOpenCamera(list.get(position).id,position,Parentposition)
        }

        editSubChildListener.onClick("",list.get(position).costume_id,list.get(position).id,list.get(position).costume.image_url)

        holder.reviewBtn.setOnClickListener {
            editSubChildListener.onClickRemark(list.get(position).costume_id.toString())
        }
    }




    class ViewHolder(view : View) :RecyclerView.ViewHolder(view){
        val img=view.findViewById<ImageView>(R.id.edit_custom_child_custom_item_img)
        val reviewBtn=view.findViewById<LinearLayout>(R.id.edit_custom_child_custom)
        val cameraOpen=view.findViewById<RelativeLayout>(R.id.sub_child_rl)
    }
}