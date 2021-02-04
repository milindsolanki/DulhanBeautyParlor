package com.example.dulhanbeautyparlor.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.dulhanbeautyparlor.BasicFragment
import com.example.dulhanbeautyparlor.R
import com.example.dulhanbeautyparlor.adapter.AdvertiseAdapter
import com.example.dulhanbeautyparlor.modalClass.*
import com.example.dulhanbeautyparlor.viewHolder.ViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.hair_specialist_adapter.view.*
import kotlinx.android.synthetic.main.home_adapter.view.*
import kotlinx.android.synthetic.main.special_offer_adapter.view.*
import kotlin.collections.ArrayList


class HomeFragment : BasicFragment() {

    // val datalist = java.util.ArrayList<AdvertiseImage>()
    val dots = arrayOfNulls<ImageView>(6)
    var indicatorlay: LinearLayout? = null
    lateinit var mAdapter: AdvertiseAdapter
    var ref: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initView(view)
        return view
    }


    private fun initView(view: View) {
        addData()
        specialOffer(view)
        specialListAdapter(view)


        homeItemRecyclerView(view)

        indicatorlay = view.findViewById(R.id.lay_indicator_home) as LinearLayout
        mAdapter = AdvertiseAdapter(context, datalist, view.home_viewpager)
        view.home_viewpager.adapter = mAdapter
//        setupIndicator(dots, mAdapter, indicatorlay!!)
//        createSlider(mAdapter, "", view.home_viewpager)
        view.home_viewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                for (i in 0 until mAdapter.itemCount) {
                    dots[i]!!
                        .setImageDrawable(
                            activity?.let {
                                ContextCompat.getDrawable(
                                    it,
                                    R.drawable.indicator_inactive
                                )
                            }
                        )
                }
                dots[position]!!
                    .setImageDrawable(
                        context?.let {
                            ContextCompat.getDrawable(
                                it,
                                R.drawable.indicator_active
                            )
                        }
                    )
            }
        })
    }

    private fun homeItemRecyclerView(view: View) {
        ref = FirebaseDatabase.getInstance().getReference("HomeItem")
        view.home_recyclerView.layoutManager =
            GridLayoutManager(context, 4)
        val homeItemAdapter = object : FirebaseRecyclerAdapter<HomeItemImage, ViewHolder>(
            HomeItemImage::class.java,
            R.layout.home_adapter,
            ViewHolder::class.java,
            ref
        ) {
            override fun populateViewHolder(p0: ViewHolder?, p1: HomeItemImage?, p2: Int) {
                p0!!.view.txt_homeitem_name.text = p1!!.name
                Glide.with(context!!).load(p1.image).into(p0.view.imagebutton_homeitem)
                p0.view.imagebutton_homeitem.setOnClickListener {
                    val appCompatActivity: AppCompatActivity
                    appCompatActivity = it.context as AppCompatActivity
                    val fragment: Fragment
                    fragment = ItemListFragment()
                    appCompatActivity.supportFragmentManager.beginTransaction()
                        .replace(R.id.bd_frame, fragment).addToBackStack(null).commit()
                }
            }
        }
        view.home_recyclerView.adapter = homeItemAdapter
    }

    private fun specialListAdapter(view: View) {
        ref = FirebaseDatabase.getInstance().getReference("HairSpecialist")
        view.home_hair_specialist.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val specialistAdapter = object : FirebaseRecyclerAdapter<SpecialList, ViewHolder>(
            SpecialList::class.java,
            R.layout.hair_specialist_adapter,
            ViewHolder::class.java,
            ref
        ) {
            override fun populateViewHolder(p0: ViewHolder?, p1: SpecialList?, p2: Int) {
                p0!!.view.txt_list_name.text = p1!!.UserName
                p0.view.ratingBar.rating = p1.Rating.toFloat()
                Glide.with(context!!).load(p1.UserImage).into(p0.view.image_list)
            }
        }
        view.home_hair_specialist.adapter = specialistAdapter
    }

    private fun specialOffer(view: View) {
        ref = FirebaseDatabase.getInstance().getReference("SpecialOffer")
        view.home_special_offer.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val specialOfferRecycler = object : FirebaseRecyclerAdapter<SpecialOffer, ViewHolder>(
            SpecialOffer::class.java,
            R.layout.special_offer_adapter,
            ViewHolder::class.java,
            ref
        ) {
            override fun populateViewHolder(p0: ViewHolder?, p1: SpecialOffer?, p2: Int) {
                p0!!.view.txt_offer_discount.text = p1!!.OfferDiscount
                p0.view.txt_offername.text = p1.OfferName
                Glide.with(context!!).load(p1.OfferImage).into(p0.view.image_offer)
            }

        }
        view.home_special_offer.adapter = specialOfferRecycler
    }


}