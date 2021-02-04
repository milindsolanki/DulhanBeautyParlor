package com.example.dulhanbeautyparlor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dulhanbeautyparlor.R
import com.example.dulhanbeautyparlor.fragment.ItemListFragment
import com.example.dulhanbeautyparlor.modalClass.HomeItem
import com.example.dulhanbeautyparlor.viewHolder.ViewHolder
import kotlinx.android.synthetic.main.home_adapter.view.*

class HomeAdapter(val context: Context?, val list: ArrayList<HomeItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.home_adapter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.txt_homeitem_name.text = list[position].name
        Glide.with(context!!).load(list[position].image).into(holder.itemView.imagebutton_homeitem)
        holder.itemView.imagebutton_homeitem.setOnClickListener {
            val appCompatActivity: AppCompatActivity
            appCompatActivity = it.context as AppCompatActivity
            val fragment: Fragment
            fragment = ItemListFragment()
            appCompatActivity.supportFragmentManager.beginTransaction()
                .replace(R.id.bd_frame, fragment).commit()


        }
    }

    override fun getItemCount(): Int = list.size
}