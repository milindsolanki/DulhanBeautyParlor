package com.example.dulhanbeautyparlor.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.dulhanbeautyparlor.R
import com.example.dulhanbeautyparlor.modalClass.SpecialOffer
import com.example.dulhanbeautyparlor.viewHolder.ViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_services.view.*
import kotlinx.android.synthetic.main.services_adapter.view.*


class ServicesFragment : Fragment() {

    var ref: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_services, container, false)
        intiView(view)
        return view
    }

    private fun intiView(view: View) {
        specialOffer(view)
    }

    private fun specialOffer(view: View) {
        ref = FirebaseDatabase.getInstance().getReference("SpecialOffer")
        view.servicer_recyclerView.layoutManager =
            GridLayoutManager(context, 2)
        val specialOfferRecycler = object : FirebaseRecyclerAdapter<SpecialOffer, ViewHolder>(
            SpecialOffer::class.java,
            R.layout.services_adapter,
            ViewHolder::class.java,
            ref
        ) {
            override fun populateViewHolder(p0: ViewHolder?, p1: SpecialOffer?, p2: Int) {
                p0!!.view.txt_services_name.text = p1!!.OfferName
                Glide.with(context!!).load(p1.OfferImage).into(p0.view.image_services)
                p0.view.services_item_image.setOnClickListener {
                    val appCompatActivity: AppCompatActivity
                    appCompatActivity = it.context as AppCompatActivity
                    val fragment: Fragment
                    fragment = ItemListFragment()
                    appCompatActivity.supportFragmentManager.beginTransaction()
                        .replace(R.id.bd_frame, fragment).addToBackStack(null).commit()
                }
            }

        }
        view.servicer_recyclerView.adapter = specialOfferRecycler
    }
}