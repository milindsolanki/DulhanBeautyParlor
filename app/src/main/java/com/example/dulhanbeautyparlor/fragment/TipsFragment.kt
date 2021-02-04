package com.example.dulhanbeautyparlor.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.dulhanbeautyparlor.R
import com.example.dulhanbeautyparlor.activity.MoreInfoActivity
import com.example.dulhanbeautyparlor.commenUtil.ITEM_DETAIL
import com.example.dulhanbeautyparlor.commenUtil.ITEM_TITLE
import com.example.dulhanbeautyparlor.commenUtil.TIP_DETAIL
import com.example.dulhanbeautyparlor.commenUtil.TIP_NAME
import com.example.dulhanbeautyparlor.modalClass.Portfolio
import com.example.dulhanbeautyparlor.modalClass.Tips
import com.example.dulhanbeautyparlor.viewHolder.ViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_portfolio.view.*
import kotlinx.android.synthetic.main.fragment_tips.view.*
import kotlinx.android.synthetic.main.portfolio_adapter.view.*
import kotlinx.android.synthetic.main.tips_adapter.view.*


class TipsFragment : Fragment() {
    var ref: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tips, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {

        ref = FirebaseDatabase.getInstance().getReference("Tips")
        view.tips_recyclerView.layoutManager =
            LinearLayoutManager(context)
        val portfolioAdapter = object : FirebaseRecyclerAdapter<Tips, ViewHolder>(
            Tips::class.java,
            R.layout.tips_adapter,
            ViewHolder::class.java,
            ref
        ) {
            override fun populateViewHolder(p0: ViewHolder?, p1: Tips?, p2: Int) {
                p0!!.view.txt_tip_name.text = p1!!.Tname
                p0.view.txt_tip.text = p1.Tip

                Glide.with(context!!).load(p1.Timage).into(p0.view.image_tip)

                p0.view.tip_layout.setOnClickListener {
                    val intent = Intent(context, MoreInfoActivity::class.java)
                    intent.putExtra(TIP_NAME, p1.Tname)
                    intent.putExtra(TIP_DETAIL, p1.Tip)
                    startActivity(intent)
                }


            }
        }
        view.tips_recyclerView.adapter = portfolioAdapter

    }


}