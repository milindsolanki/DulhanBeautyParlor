package com.example.dulhanbeautyparlor.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dulhanbeautyparlor.BasicActivity
import com.example.dulhanbeautyparlor.R
import com.example.dulhanbeautyparlor.modalClass.ArtistList
import com.example.dulhanbeautyparlor.modalClass.AppointmentTime
import com.example.dulhanbeautyparlor.viewHolder.ViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_appoinment.*
import kotlinx.android.synthetic.main.activity_appoinment.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.hair_specialist_adapter.view.*
import kotlinx.android.synthetic.main.time_adapter.*
import kotlinx.android.synthetic.main.time_adapter.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AppointmentActivity : BasicActivity(){

    var ref: DatabaseReference? = null
    val time = ArrayList<AppointmentTime>()
    var getDate: String = ""
    var getTime: String = ""
    var artistName: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appoinment)

        txt_title.text = resources.getString(R.string.appoinment)
        image_back.setImageResource(R.drawable.ic_back_48)

        image_back.setOnClickListener {
            finish()
        }
        initView()

    }

    @SuppressLint("SimpleDateFormat")
    private fun initView() {
        setTime()
//        date_recyclerView.layoutManager = GridLayoutManager(this, 4)
//        date_recyclerView.adapter = TimeAdapter(this, time, this)

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val date = sdf.format(calendar.date)
        getDate = date
        calendar.minDate = System.currentTimeMillis() - 1000

        ref = FirebaseDatabase.getInstance().getReference("ArtistList")
        selectArtist_recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val specialistAdapter = object : FirebaseRecyclerAdapter<ArtistList, ViewHolder>(
            ArtistList::class.java,
            R.layout.hair_specialist_adapter,
            ViewHolder::class.java,
            ref
        ) {
            override fun populateViewHolder(p0: ViewHolder?, p1: ArtistList?, p2: Int) {
                p0!!.view.txt_list_name.text = p1!!.artistName
                p0.view.ratingBar.visibility = View.GONE
                Glide.with(this@AppointmentActivity).load(p1.artistImage).into(p0.view.image_list)
                p0.view.image_list.setOnClickListener {
                    artistName = p1.artistName
                    profileDialog(p1.artistImage, p1.artistName, p1.artistRat, p1.artistDetail)
                }

            }
        }
        selectArtist_recyclerView.adapter = specialistAdapter
    }

    private fun setTime() {
        ref = FirebaseDatabase.getInstance().getReference("AppointmentTime")
        date_recyclerView.layoutManager =
            GridLayoutManager(this, 4)
        val dateAdapter = object : FirebaseRecyclerAdapter<AppointmentTime, ViewHolder>(
            AppointmentTime::class.java,
            R.layout.time_adapter,
            ViewHolder::class.java,
            ref
        ) {
            override fun populateViewHolder(p0: ViewHolder?, p1: AppointmentTime?, p2: Int) {
                p0!!.view.txt_time.text = p1!!.Time
                p0.view.txt_time.setOnClickListener {
                    getTime = p1.Time
                }

            }
        }
        date_recyclerView.adapter = dateAdapter
    }

    fun confirmBooking(view: View) {
        if (getDate.isEmpty()) {
            Toast.makeText(this, "Plase select Date", Toast.LENGTH_SHORT).show()
        } else if (getTime.isEmpty()) {
            Toast.makeText(this, "Plase select Time", Toast.LENGTH_SHORT).show()
        } else if (artistName.isEmpty()) {
            Toast.makeText(this, "Plase select Artist", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Appointment Book", Toast.LENGTH_SHORT)
                .show()

        }
    }


}