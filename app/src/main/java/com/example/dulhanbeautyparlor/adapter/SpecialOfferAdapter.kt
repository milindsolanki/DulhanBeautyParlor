package com.example.dulhanbeautyparlor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dulhanbeautyparlor.R
import com.example.dulhanbeautyparlor.modalClass.SpecialOffer
import com.example.dulhanbeautyparlor.viewHolder.ViewHolder
import kotlinx.android.synthetic.main.special_offer_adapter.view.*

class SpecialOfferAdapter(val context: Context, val list: ArrayList<SpecialOffer>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.special_offer_adapter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        /*holder.itemView.txt_offername.text = list[position].offerImage
        holder.itemView.txt_offer_discount.text = list[position].offerDiscount
        Glide.with(context).load(list[position].offerImage).into(holder.itemView.image_offer)*/
    }

    override fun getItemCount(): Int = list.size

}