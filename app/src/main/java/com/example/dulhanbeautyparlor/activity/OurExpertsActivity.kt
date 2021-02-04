package com.example.dulhanbeautyparlor.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dulhanbeautyparlor.R
import com.example.dulhanbeautyparlor.modalClass.ArtistData
import com.example.dulhanbeautyparlor.modalClass.SpecialList
import com.example.dulhanbeautyparlor.viewHolder.ViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_our_experts.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.hair_specialist_adapter.view.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.txt_title

class OurExpertsActivity : AppCompatActivity() {
    var ref: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_our_experts)
        txt_title.text = resources.getString(R.string.our_experts)
        image_back.setImageResource(R.drawable.ic_back_48)

        image_back.setOnClickListener {
            finish()
        }
        intiView()
    }

    private fun intiView() {
        makeUpArtist()
        hairStyle()
        nailArt()
    }

    private fun nailArt() {
        ref = FirebaseDatabase.getInstance().getReference("MakeUpArtist")
        makeup_artist_recycleView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val specialistAdapter = object : FirebaseRecyclerAdapter<ArtistData, ViewHolder>(
            ArtistData::class.java,
            R.layout.hair_specialist_adapter,
            ViewHolder::class.java,
            ref
        ) {
            override fun populateViewHolder(p0: ViewHolder?, p1: ArtistData?, p2: Int) {
                p0!!.view.txt_list_name.text = p1!!.ArtistName
                p0.view.ratingBar.rating = p1.Rating.toFloat()
                Glide.with(this@OurExpertsActivity).load(p1.ArtistImage).into(p0.view.image_list)

            }
        }
        makeup_artist_recycleView.adapter = specialistAdapter
    }

    private fun hairStyle() {
        ref = FirebaseDatabase.getInstance().getReference("MakeUpArtist")
        hair_style_recycleView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val specialistAdapter = object : FirebaseRecyclerAdapter<ArtistData, ViewHolder>(
            ArtistData::class.java,
            R.layout.hair_specialist_adapter,
            ViewHolder::class.java,
            ref
        ) {
            override fun populateViewHolder(p0: ViewHolder?, p1: ArtistData?, p2: Int) {
                p0!!.view.txt_list_name.text = p1!!.ArtistName
                p0.view.ratingBar.rating = p1.Rating.toFloat()
                Glide.with(this@OurExpertsActivity).load(p1.ArtistImage).into(p0.view.image_list)

            }
        }
        hair_style_recycleView.adapter = specialistAdapter
    }

    private fun makeUpArtist() {
        ref = FirebaseDatabase.getInstance().getReference("MakeUpArtist")
        nail_art_recycleView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val specialistAdapter = object : FirebaseRecyclerAdapter<ArtistData, ViewHolder>(
            ArtistData::class.java,
            R.layout.hair_specialist_adapter,
            ViewHolder::class.java,
            ref
        ) {
            override fun populateViewHolder(p0: ViewHolder?, p1: ArtistData?, p2: Int) {
                p0!!.view.txt_list_name.text = p1!!.ArtistName
                p0.view.ratingBar.rating = p1.Rating.toFloat()
                Glide.with(this@OurExpertsActivity).load(p1.ArtistImage).into(p0.view.image_list)

            }
        }
        nail_art_recycleView.adapter = specialistAdapter
    }
}