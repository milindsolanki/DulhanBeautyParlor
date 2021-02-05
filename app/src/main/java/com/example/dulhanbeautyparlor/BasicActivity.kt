package com.example.dulhanbeautyparlor

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.dulhanbeautyparlor.activity.MainActivity
import com.example.dulhanbeautyparlor.adapter.AdvertiseAdapter
import com.example.dulhanbeautyparlor.commenUtil.PreferenceHelper
import com.example.dulhanbeautyparlor.interfaces.ButtonOk
import com.example.dulhanbeautyparlor.modalClass.AdvertiseImage
import com.example.dulhanbeautyparlor.modalClass.HomeItem
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_advertise.*
import kotlinx.android.synthetic.main.alert_dilog.view.*
import kotlinx.android.synthetic.main.profile_dialog.view.*
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

open class BasicActivity : AppCompatActivity() {
    lateinit var sharPref: SharedPreferences
    val datalist = ArrayList<AdvertiseImage>()
    var currentPage: Int = 0

    var PASSWORD_PATTERN = Pattern.compile(
        "^" +
                "(?=.*[@#$%^&+=])" +  // at least 1 special character
                "(?=\\S+$)" +  // no white spaces
                ".{4,}" +  // at least 4 characters
                "$"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharPref = PreferenceHelper.customPrefs(this)
    }

    fun addData() {
        datalist.add(AdvertiseImage(R.drawable.one))
        datalist.add(AdvertiseImage(R.drawable.two))
        datalist.add(AdvertiseImage(R.drawable.three))
        datalist.add(AdvertiseImage(R.drawable.four))
        datalist.add(AdvertiseImage(R.drawable.five))

    }

    fun createSlider(mAdapter: AdvertiseAdapter, intent: String, viewpager: ViewPager2) {

        val handler = Handler()
        val Update = Runnable {

             if (intent.isEmpty()) {
                 if (currentPage == mAdapter.itemCount) {
                     currentPage = 0

                 }
             } else {
                 if (currentPage == mAdapter.itemCount) {
                     startActivity(Intent(this, MainActivity::class.java))
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
        //val indicatorlay = findViewById(R.id.lay_indicator) as LinearLayout

        for (i in 0 until mAdapter.itemCount) {
            dots[i] = ImageView(this)
            dots[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
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
                getApplicationContext(),
                R.drawable.indicator_active
            )
        )
    }


    fun dialogBuilder(title: String, ok: ButtonOk?) {

        val mdialogBuilder = LayoutInflater.from(this).inflate(R.layout.alert_dilog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mdialogBuilder)
        // create dialog box

        val alert = mBuilder.show()
        // set title for alert dialog box
        mdialogBuilder.txt_title_dilog.text = title
        mdialogBuilder.btn_cancle.setOnClickListener {
            alert.dismiss()
        }
        mdialogBuilder.btn_ok.setOnClickListener {
            ok!!.onOkClick()
            finish()
        }
        // show alert dialog
        alert.show()
    }

    /*fun alertDilog(title, ok: ButtonBarLayout) {


    }*/
    fun profileDialog(
        profileImage: String,
        artistName: String,
        ratingBar: Int,
        moreInfo: String
    ) {
        val mDialog = LayoutInflater.from(this).inflate(R.layout.profile_dialog, null)
        val mBundle = AlertDialog.Builder(this)
            .setView(mDialog)
        val mAlertDialog = mBundle.show()
        mDialog.cancel_image.setOnClickListener {
            mAlertDialog.dismiss()
        }
        mDialog.txt_porfile_dialog_name.text = artistName
        mDialog.ratingBar_dialog.rating = ratingBar.toFloat()
        mDialog.txt_porfile_dialog_info.text = moreInfo
        Glide.with(this).load(profileImage).into(mDialog.dialog_profile_image)
    }
}