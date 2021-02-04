package com.example.dulhanbeautyparlor.activity

import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.dulhanbeautyparlor.BasicActivity
import com.example.dulhanbeautyparlor.R
import com.example.dulhanbeautyparlor.adapter.AdvertiseAdapter
import com.example.dulhanbeautyparlor.commenUtil.ITEM_DETAIL
import com.example.dulhanbeautyparlor.commenUtil.ITEM_TITLE
import com.example.dulhanbeautyparlor.commenUtil.TIP_DETAIL
import com.example.dulhanbeautyparlor.commenUtil.TIP_NAME
import com.example.dulhanbeautyparlor.modalClass.AdvertiseImage
import com.example.dulhanbeautyparlor.modalClass.HomeItem
import kotlinx.android.synthetic.main.activity_more_info.*
import kotlinx.android.synthetic.main.toolbar.*

class MoreInfoActivity : BasicActivity() {
    var tipName: String = ""
    var tipDetail: String = ""
    var infoName: String = ""
    var infoDetail: String = ""
    val dots = arrayOfNulls<ImageView>(6)
    lateinit var indicatorlay: LinearLayout
    lateinit var mAdapter: AdvertiseAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_info)

        indicatorlay = findViewById<LinearLayout>(R.id.lay_indicator_more_info)
        image_back.setImageResource(R.drawable.ic_back_48)
        btn_booking.visibility = View.GONE
        image_back.setOnClickListener {
            onBackPressed()
        }
        intiView()
    }

    private fun intiView() {

        addData()
        mAdapter = AdvertiseAdapter(this, datalist, more_info_viewpager)
        more_info_viewpager.adapter = mAdapter
        setupIndicator(dots, mAdapter, indicatorlay)
        createSlider(mAdapter, "", more_info_viewpager)

        //from tip fragment
        tipName = intent.getStringExtra(TIP_NAME).toString()
        tipDetail = intent.getStringExtra(TIP_DETAIL).toString()
        Log.e("LogName", tipName)
        Log.e("LogDetail", tipDetail)
        if (tipName.isNotEmpty()) {
            txt_item_name.setText(tipName)
            txt_more_info.setText(tipDetail)
        }


        //from ItemList fragment
        infoName = intent.getStringExtra(ITEM_TITLE).toString()
        infoDetail = intent.getStringExtra(ITEM_DETAIL).toString()
        Log.e("LogInfoName", infoName)
        Log.e("LogInfoDetail", infoDetail)
        if (!infoName.isEmpty()) {
            btn_booking.visibility = View.VISIBLE
            txt_title.text = infoName
            txt_item_name.text = infoName
            txt_more_info.text = infoDetail
        }


        more_info_viewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
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

    fun onClickBooking(view: View) {
        startActivity(Intent(this, AppointmentActivity::class.java))
    }
}