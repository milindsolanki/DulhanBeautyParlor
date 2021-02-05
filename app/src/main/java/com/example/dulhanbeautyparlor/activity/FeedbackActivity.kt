package com.example.dulhanbeautyparlor.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.dulhanbeautyparlor.BasicActivity
import com.example.dulhanbeautyparlor.R
import kotlinx.android.synthetic.main.activity_feedback.*
import kotlinx.android.synthetic.main.toolbar.*
import com.example.dulhanbeautyparlor.commenUtil.PreferenceHelper.get
import com.example.dulhanbeautyparlor.commenUtil.USER_EMAIL
import com.example.dulhanbeautyparlor.modalClass.FeedBack
import com.google.firebase.database.FirebaseDatabase

class FeedbackActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        txt_title.text = resources.getString(R.string.feedback)
        image_back.setImageResource(R.drawable.ic_back_48)

        image_back.setOnClickListener {
            finish()
        }
        txt_name_feedback.text = sharPref[USER_EMAIL, ""]
        initView()
    }

    private fun initView() {
        btn_submit.setOnClickListener {
            val rat=ratingBarFeedBack.rating
            if (rat.equals(0.0.toFloat())){
                Toast.makeText(this, "Please apply Rat. ", Toast.LENGTH_SHORT).show()
            } else {
                val database = FirebaseDatabase.getInstance().getReference("FeedBack")
                val detail = database.push().key
                val feedBack = FeedBack(
                    feedbackUser = txt_name_feedback.text.toString().trim(),
                    feedbackRat = ratingBarFeedBack.rating.toString().trim(),
                    feedbackMesage = edt_feedback_massage.text.toString().trim()
                )
                database.child(detail!!).setValue(feedBack).addOnCompleteListener {

                    ratingBarFeedBack.rating = 0.toFloat()
                    edt_feedback_massage.text = null
                }
            }

        }


    }

}