package com.example.dulhanbeautyparlor.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dulhanbeautyparlor.R
import com.example.dulhanbeautyparlor.activity.AppointmentActivity
import com.example.dulhanbeautyparlor.activity.MoreInfoActivity
import com.example.dulhanbeautyparlor.commenUtil.ITEM_DETAIL
import com.example.dulhanbeautyparlor.commenUtil.ITEM_TITLE
import com.example.dulhanbeautyparlor.modalClass.ItemList
import com.example.dulhanbeautyparlor.viewHolder.ViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_item_list.view.*
import kotlinx.android.synthetic.main.item_adapter.view.*
import kotlinx.android.synthetic.main.toolbar.*


class ItemListFragment : Fragment() {

    var ref: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        initView(view)
        // Inflate the layout for this fragment
        return view
    }

    private fun initView(view: View) {

        ref = FirebaseDatabase.getInstance().getReference("ItemList")
        view.item_list_recyclerView.layoutManager =
            LinearLayoutManager(context)
        val specialOfferRecycler = object : FirebaseRecyclerAdapter<ItemList, ViewHolder>(
            ItemList::class.java,
            R.layout.item_adapter,
            ViewHolder::class.java,
            ref
        ) {
            override fun populateViewHolder(p0: ViewHolder?, p1: ItemList?, p2: Int) {
                p0!!.view.txt_item_image_lable.text = p1!!.ItemName
                p0!!.view.txt_item_detail.text = p1!!.ItemDetail
                Glide.with(context!!).load(p1.ItemImage).into(p0.view.image_item)
                p0.view.btn_item_book.setOnClickListener {
                    context!!.startActivity(Intent(context, AppointmentActivity::class.java))
                }
                p0.view.item_layout.setOnClickListener {
                    val intent = Intent(context, MoreInfoActivity::class.java)
                    intent.putExtra(ITEM_DETAIL, p1.ItemDetail)
                    intent.putExtra(ITEM_TITLE, p1.ItemName)
                    startActivity(intent)
                }
            }

        }
        view.item_list_recyclerView.adapter = specialOfferRecycler
    }


}