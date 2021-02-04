package com.example.dulhanbeautyparlor.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dulhanbeautyparlor.R
import com.example.dulhanbeautyparlor.modalClass.Reciews
import com.example.dulhanbeautyparlor.viewHolder.ViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_our_experts.*
import kotlinx.android.synthetic.main.activity_reviews.*
import kotlinx.android.synthetic.main.hair_specialist_adapter.view.*
import kotlinx.android.synthetic.main.reviwes_adapter.view.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.txt_title

class ReviewsActivity : AppCompatActivity() {
    var ref: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)
        txt_title.text = resources.getString(R.string.reviews)
        image_back.setImageResource(R.drawable.ic_back_48)

        image_back.setOnClickListener {
            finish()
        }
        initView()
    }

    private fun initView() {

        ref = FirebaseDatabase.getInstance().getReference("Reciews")
        review_recyclerView.layoutManager =
            LinearLayoutManager(this)
        val specialistAdapter = object : FirebaseRecyclerAdapter<Reciews, ViewHolder>(
            Reciews::class.java,
            R.layout.reviwes_adapter,
            ViewHolder::class.java,
            ref
        ) {
            override fun populateViewHolder(p0: ViewHolder?, p1: Reciews?, p2: Int) {
                p0!!.view.txt_reviews_name.text = p1!!.ReviewName
                p0.view.txt_reviews.text = p1.Reviews

                p0.view.ratingBar_review.rating = p1.ReviewRat.toFloat()

                Glide.with(this@ReviewsActivity).load(p1.ReviewImage).into(p0.view.image_review)

            }
        }
        review_recyclerView.adapter = specialistAdapter
    }
}