package com.example.dulhanbeautyparlor.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.dulhanbeautyparlor.R
import com.example.dulhanbeautyparlor.modalClass.Portfolio
import com.example.dulhanbeautyparlor.modalClass.Reciews
import com.example.dulhanbeautyparlor.viewHolder.ViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_reviews.*
import kotlinx.android.synthetic.main.fragment_portfolio.*
import kotlinx.android.synthetic.main.fragment_portfolio.view.*
import kotlinx.android.synthetic.main.portfolio_adapter.view.*
import kotlinx.android.synthetic.main.reviwes_adapter.view.*


class PortFolioFragment : Fragment() {
    var ref: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_portfolio, container, false)
        initViwe(view)
        return view
    }

    private fun initViwe(view: View) {
        ref = FirebaseDatabase.getInstance().getReference("Portfolio")
        view.portfolio_recyclerView.layoutManager =
            StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        val portfolioAdapter = object : FirebaseRecyclerAdapter<Portfolio, ViewHolder>(
            Portfolio::class.java,
            R.layout.portfolio_adapter,
            ViewHolder::class.java,
            ref
        ) {
            override fun populateViewHolder(p0: ViewHolder?, p1: Portfolio?, p2: Int) {
                p0!!.view.txt_portfolio_name.text = p1!!.Pname


                Glide.with(this@PortFolioFragment).load(p1.Pimage).into(p0.view.image_portfolio)

            }
        }
        view.portfolio_recyclerView.adapter = portfolioAdapter
    }


}