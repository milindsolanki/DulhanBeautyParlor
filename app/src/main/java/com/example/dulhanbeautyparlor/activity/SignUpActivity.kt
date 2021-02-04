package com.example.dulhanbeautyparlor.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dulhanbeautyparlor.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    var PASSWORD_PATTERN = Pattern.compile(
        "^" +
                "(?=.*[@#$%^&+=])" +  // at least 1 special character
                "(?=\\S+$)" +  // no white spaces
                ".{4,}" +  // at least 4 characters
                "$"
    )

    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mAuth = FirebaseAuth.getInstance()
        btn_signUp.setOnClickListener(this)
        txt_signIn.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v) {
            btn_signUp -> {
                if (checkValidation()) {
                    signUpFirebase()
                }
            }
            txt_signIn -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    private fun checkValidation(): Boolean {
        if (edt_username.text.toString().isEmpty()) {
            edt_username.error = (getResources().getString(R.string.email_username));
            return false
        } else if (edt_email.text.toString().isEmpty()) {
            edt_email.error = (getResources().getString(R.string.email_error))
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edt_email.getText().toString()).matches()) {
            edt_email.error = (getResources().getString(R.string.error_invalid_email))
            return false
        } else if (edt_mobilenumber.text.toString().isEmpty()) {
            edt_mobilenumber.error = (getResources().getString(R.string.email_mobilenumber));
            return false
        } else if (edt_mobilenumber.text.toString().length < 9) {
            edt_mobilenumber.error = (getResources().getString(R.string.email_mobileTenDigit));
            return false
        } else if (edt_password.text.toString().isEmpty()) {
            edt_password.error = (getResources().getString(R.string.enter_valid_data))
            return false
        } else if (edt_password.text.toString().length < 6) {
            edt_password.error = (getResources().getString(R.string.enter_min_digit))
            return false
        } else if (!PASSWORD_PATTERN.matcher(edt_password.text.toString()).matches()) {
            edt_password.error = (getResources().getString(R.string.password_is_too_weak))
            return false
        } else if (edt_confirm_password.text.toString().isEmpty()) {
            edt_confirm_password.error = (getResources().getString(R.string.enter_valid_data))
            return false
        } else if (edt_confirm_password.text.toString().length < 6) {
            edt_confirm_password.error = (getResources().getString(R.string.enter_min_digit))
            return false
        } else if (edt_confirm_password.text.toString().trim()
            != (edt_password.text.toString().trim())
        ) {
            edt_confirm_password.error = (getResources().getString(R.string.enter_same_password))
            return false
        } else if (edt_confirm_password.text.toString() != edt_password.text.toString()) {
            edt_confirm_password.error =
                (getResources().getString(R.string.password_does_not_match))
        }
        return true
    }

    private fun signUpFirebase() {
        mAuth!!.createUserWithEmailAndPassword(
            edt_email.text.toString(),
            edt_password.text.toString()
        ).addOnCompleteListener(
            this@SignUpActivity,
            object : OnCompleteListener<AuthResult> {
                override fun onComplete(p0: Task<AuthResult>) {
                    if (p0.isSuccessful()) {
                        startActivity(
                            Intent(
                                this@SignUpActivity,
                                LoginActivity::class.java
                            )

                        )
                        finish()
                    } else {
                        Log.w("TAG", "createUserWithEmail:failure", p0.getException())
                        Toast.makeText(
                            this@SignUpActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            })
    }
}