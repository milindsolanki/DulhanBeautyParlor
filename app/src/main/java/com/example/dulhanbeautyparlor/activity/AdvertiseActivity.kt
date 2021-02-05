package com.example.dulhanbeautyparlor.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.dulhanbeautyparlor.BasicActivity
import com.example.dulhanbeautyparlor.R
import com.example.dulhanbeautyparlor.adapter.AdvertiseAdapter
import com.example.dulhanbeautyparlor.modalClass.AdvertiseImage
import com.example.dulhanbeautyparlor.modalClass.HomeItem
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_advertise.*
import kotlin.collections.ArrayList


class AdvertiseActivity : BasicActivity() {
    var indicatorlay: LinearLayout? = null
    lateinit var mAdapter: AdvertiseAdapter


    val dots = arrayOfNulls<ImageView>(6)
    val addImage = ArrayList<AdvertiseImage>()


    private var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advertise)
        intiView()
    }

    private fun intiView() {
        indicatorlay = findViewById(R.id.lay_indicator) as LinearLayout
        addImage()

        txt_skip.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        addData()

        mAdapter = AdvertiseAdapter(this, addImage, advertise_viewpager)
        advertise_viewpager.adapter = AdvertiseAdapter(this, addImage, advertise_viewpager)

        setupIndicator(dots, mAdapter, indicatorlay!!)
        createSlider(mAdapter, "", advertise_viewpager)

        advertise_viewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // val dots = arrayOfNulls<ImageView>(mAdapter.itemCount)
                for (i in 0 until mAdapter.itemCount) {
                    dots[i]!!
                        .setImageDrawable(
                            ContextCompat.getDrawable(
                                getApplicationContext(),
                                R.drawable.indicator_inactive
                            )
                        )
                }
                dots[position]!!
                    .setImageDrawable(
                        ContextCompat.getDrawable(
                            getApplicationContext(),
                            R.drawable.indicator_active
                        )
                    )
            }
        })


    }

    private fun addImage() {
        addImage.add(AdvertiseImage(R.drawable.one))
        addImage.add(AdvertiseImage(R.drawable.two))
        addImage.add(AdvertiseImage(R.drawable.three))
        addImage.add(AdvertiseImage(R.drawable.one))
        addImage.add(AdvertiseImage(R.drawable.two))
    }

    private fun getFireBaseData(advertiseActivity: AdvertiseActivity) {

        mDatabase = FirebaseDatabase.getInstance().getReference("AdvertiseImage")
        mDatabase!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    val data = i.getValue(AdvertiseImage::class.java)
                    Log.e("Log", data.toString())
                    val advertiseImage = AdvertiseImage()
                    advertiseImage.image = data!!.image
                    //addData.add(advertiseImage)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}



