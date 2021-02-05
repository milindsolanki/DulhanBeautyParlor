package com.example.dulhanbeautyparlor.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.dulhanbeautyparlor.R
import com.example.dulhanbeautyparlor.modalClass.AdvertiseImage
import com.example.dulhanbeautyparlor.modalClass.HomeItem
import com.example.dulhanbeautyparlor.viewHolder.ViewHolder
import kotlinx.android.synthetic.main.advertise_list.view.*

class AdvertiseAdapter(
    val context: Context?,
    val list: List<AdvertiseImage>,
    val viewPager2: ViewPager2
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.advertise_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(context!!).load(list[position].image).centerCrop()
            .into(holder.itemView.advertise_image)
    }

    override fun getItemCount(): Int =list.size


}