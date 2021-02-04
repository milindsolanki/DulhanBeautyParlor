package com.example.dulhanbeautyparlor.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.dulhanbeautyparlor.BasicActivity
import com.example.dulhanbeautyparlor.R
import com.example.dulhanbeautyparlor.commenUtil.USER_EMAIL
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

import com.example.dulhanbeautyparlor.commenUtil.PreferenceHelper.set
import com.example.dulhanbeautyparlor.commenUtil.PreferenceHelper.get
import com.example.dulhanbeautyparlor.modalClass.AdvertiseImage
import com.example.dulhanbeautyparlor.modalClass.HomeItem
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception


class LoginActivity : BasicActivity(), View.OnClickListener {
    var name: String? = null
    var email: String? = null
    var photoUrl: Uri? = null
    var homeImage: HomeItem? = null
    var advertiseimage: AdvertiseImage? = null
    val homeItem = ArrayList<HomeItem>()


    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialization()
        initView()
    }

    private fun initView() {
        homeItemData()
        addData()
        advertiseImage()
        insertImage()

    }

    private fun advertiseImage() {

        if (sharPref[USER_EMAIL, ""] == "")
            for (i in 0..datalist.size - 1) {
                val database = FirebaseDatabase.getInstance().getReference("AdvertiseImage")
                val detail = database.push().key
                try {
                    advertiseimage = AdvertiseImage(datalist[i].image)
                } catch (e: Exception) {

                }

                database.child(detail!!).setValue(advertiseimage).addOnCompleteListener {
                    //Toast.makeText(this, "send Done", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun insertImage() {

        for (i in 0..homeItem.size - 1) {
            val database = FirebaseDatabase.getInstance().getReference("HomeItem")
            val detail = database.push().key
            try {
                homeImage = HomeItem(homeItem[i].image, homeItem[i].name)
            } catch (e: Exception) {

            }

            database.child(detail!!).setValue(homeImage).addOnCompleteListener {
                //Toast.makeText(this, "send Done", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun homeItemData() {
        homeItem.add(HomeItem(R.drawable.make_up, "Make up"))
        homeItem.add(HomeItem(R.drawable.hair_cut, "Hair cut"))
        homeItem.add(HomeItem(R.drawable.hair_style, "Hair Style"))
        homeItem.add(HomeItem(R.drawable.hair_spa, "Hair Spa"))
        homeItem.add(HomeItem(R.drawable.facial, "Facial"))
        homeItem.add(HomeItem(R.drawable.hair_color, "Hair Color"))
        homeItem.add(HomeItem(R.drawable.nail_art, "Nail Art"))
        homeItem.add(HomeItem(R.drawable.more, "More"))

    }

    private fun initialization() {
        mAuth = FirebaseAuth.getInstance()
        btn_signin.setOnClickListener(this)
        txt_forgot_password.setOnClickListener(this)
        txt_signup.setOnClickListener(this)
        txt_skip.setOnClickListener(this)
    }

    private fun userDetail() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // Name, email address, and profile photo Url
            name = user.displayName
            email = user.email


            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project.
            val uid = user.uid

        }
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_signin -> {
                if (checkValidation()) {
                    progressbar.visibility = View.VISIBLE
                    mAuth!!.signInWithEmailAndPassword(
                        edt_emailId.text.toString(),
                        edt_pwd.text.toString()
                    )
                        .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                            override fun onComplete(p0: Task<AuthResult>) {
                                if (p0.isSuccessful()) {
                                    progressbar.visibility = View.GONE
                                    sharPref[USER_EMAIL] = edt_emailId.text.toString().trim()
                                    startActivity(
                                        Intent(
                                            this@LoginActivity,
                                            AdvertiseActivity::class.java
                                        )
                                    )
                                } else {
                                    progressbar.visibility = View.GONE
                                    Log.w("TAG", "createUserWithEmail:failure", p0.getException())
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Sorry User And PassWord Doesn't Match.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        })
                }
            }
            txt_signup -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
            txt_forgot_password -> {
                forgotPassWord()
            }
            txt_skip -> {
                startActivity(Intent(this, AdvertiseActivity::class.java))
            }
        }
    }

    private fun checkValidation(): Boolean {

        if (edt_emailId.text.toString().isEmpty()) {
            edt_emailId.error = (getResources().getString(R.string.email_error))
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edt_emailId.getText().toString())
                .matches()
        ) {
            edt_emailId.error = (getResources().getString(R.string.error_invalid_email))
            return false
        } else if (edt_pwd.text.toString().isEmpty()) {
            edt_pwd.error = (getResources().getString(R.string.enter_valid_data))
            return false
        } else if (edt_pwd.text.toString().length < 6) {
            edt_pwd.error = (getResources().getString(R.string.enter_min_digit))
            return false
        }
        return true
    }

    private fun forgotPassWord() {
        val editText = EditText(this)
        val progressBar = ProgressBar(this)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        editText.setLayoutParams(layoutParams)
        AlertDialog.Builder(this)
            .setTitle("Forgot PassWord")
            .setMessage("Please Enter Email...")
            .setView(editText)
            .setPositiveButton("OK") { dialog, which ->
                if (editText.text.toString() == "") {
                    editText.setError("askncnajsnc")
                    editText.requestFocus()
                } else {
                    progressBar.visibility = View.VISIBLE
                    mAuth!!.sendPasswordResetEmail(editText.text.toString())
                        .addOnCompleteListener(object : OnCompleteListener<Void> {
                            override fun onComplete(p0: Task<Void>) {
                                if (p0.isSuccessful()) {
                                    progressBar.visibility = View.GONE
                                    Toast.makeText(

                                        this@LoginActivity,
                                        "We have sant your instructions on Email to reset your password!",
                                        Toast.LENGTH_SHORT
                                    ).show()


                                } else {
                                    progressBar.visibility = View.GONE
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Failed to sand reset email!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            }
                        })
                }

            }
            .setNegativeButton("Cancel") { dialog, which ->
                progressBar.visibility = View.GONE
            }
            .show()


    }

}

