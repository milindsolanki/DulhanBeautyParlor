package com.example.dulhanbeautyparlor.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.dulhanbeautyparlor.R
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.toolbar.*

class AboutUsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)


        txt_title.text = resources.getString(R.string.about_us)
        image_back.setImageResource(R.drawable.ic_back_48)

        image_back.setOnClickListener {
            finish()
        }
        web_about.loadUrl("https://waytocode.in/contact.html")
    }

}