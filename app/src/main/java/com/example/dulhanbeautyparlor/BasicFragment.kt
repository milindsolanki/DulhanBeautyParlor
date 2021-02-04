package com.example.dulhanbeautyparlor

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.dulhanbeautyparlor.activity.MainActivity
import com.example.dulhanbeautyparlor.adapter.AdvertiseAdapter
import com.example.dulhanbeautyparlor.modalClass.AdvertiseImage
import com.example.dulhanbeautyparlor.modalClass.HomeItem
import kotlinx.android.synthetic.main.activity_advertise.*
import java.util.*


open class BasicFragment : Fragment() {
    var currentPage: Int = 0
    val datalist = ArrayList<AdvertiseImage>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_basic, container, false)
        intiView(view)
        // Inflate the layout for this fragment
        return view
    }

    private fun intiView(view: View) {

    }
    fun addData() {
        datalist.add(AdvertiseImage(R.drawable.one))
        datalist.add(AdvertiseImage(R.drawable.two))
        datalist.add(AdvertiseImage(R.drawable.three))
        datalist.add(AdvertiseImage(R.drawable.four))
        datalist.add(AdvertiseImage(R.drawable.five))

    }

    fun createSlider(mAdapter: AdvertiseAdapter, intent: String,viewpager:ViewPager2) {

        val handler = Handler()
        val Update = Runnable {
            if (intent.isEmpty()) {
                if (currentPage == mAdapter.itemCount) {
                    currentPage = 0

                }
            } else {
                if (currentPage == mAdapter.itemCount) {
                    startActivity(Intent(context, MainActivity::class.java))
                }
            }
            viewpager.setCurrentItem(currentPage++, true)


        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 500, 3000)
    }

    fun setupIndicator(
        dots: Array<ImageView?>,
        mAdapter: AdvertiseAdapter,
        indicatorlay: LinearLayout
    ) {
        //val indicatorlay = view.findViewById(R.id.lay_indicator) as LinearLayout

        for (i in 0 until mAdapter.itemCount) {
            dots[i] = ImageView(context)
            dots[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.indicator_inactive
                )
            )
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            indicatorlay.addView(dots[i], params)
        }
        dots[0]!!.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.indicator_active
            )
        )
    }
}