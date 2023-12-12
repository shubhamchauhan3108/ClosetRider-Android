package com.arramton.closet.rider.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.arramton.closet.rider.R
import com.arramton.closet.rider.listener.RightNavigationListener
import com.arramton.closet.rider.model.RightNavigationModel
import com.bumptech.glide.Glide

class RightNavigationAdapter(var rightNavList: List<RightNavigationModel>, var context: Context, var rightNavListener: RightNavigationListener):RecyclerView.Adapter<RightNavigationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RightNavigationAdapter.ViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.custom_right_navigation_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RightNavigationAdapter.ViewHolder, position: Int) {
        holder.iconName.text=rightNavList.get(position).leftDrawerTitle;
        Glide.with(context).load(rightNavList.get(position).leftDrawerImage).into(holder.imgIcon)
        holder.itemView.setOnClickListener {
            rightNavListener.onClickNavigation(rightNavList.get(position).leftDrawerTitle)

        }

    }

    override fun getItemCount(): Int {
       return rightNavList.size
    }

    class ViewHolder(view :View) : RecyclerView.ViewHolder(view){

        val imgIcon=view.findViewById<ImageView>(R.id.custom_right_drawer_item_image);
        val iconName=view.findViewById<TextView>(R.id.custom_right_drawer_item_title)
    }
}