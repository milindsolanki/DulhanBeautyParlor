package com.example.dulhanbeautyparlor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dulhanbeautyparlor.R
import com.example.dulhanbeautyparlor.interfaces.GetTime
import com.example.dulhanbeautyparlor.modalClass.AppointmentTime
import com.example.dulhanbeautyparlor.viewHolder.ViewHolder
import kotlinx.android.synthetic.main.time_adapter.view.*

class TimeAdapter(val context: Context, val appointmentTime: ArrayList<AppointmentTime>, val selectTime:GetTime) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.time_adapter, parent, false)
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.txt_time.text = appointmentTime[position].Time
    }

    override fun getItemCount(): Int = appointmentTime.size
}