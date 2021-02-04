package com.example.dulhanbeautyparlor.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dulhanbeautyparlor.R
import kotlinx.android.synthetic.main.toolbar.*

class FeedbackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        txt_title.text = resources.getString(R.string.feedback)
        image_back.setImageResource(R.drawable.ic_back_48)

        image_back.setOnClickListener {
            finish()
        }
    }
}