package com.ssn.studentapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ssn.studentapp.api.RetrofitClient
import com.ssn.studentapp.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login : AppCompatActivity(), OnClickListener {

    private lateinit var emailAddress: EditText
    private lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailAddress = findViewById(R.id.editTextUserEmail)
        password = findViewById(R.id.editTextUserPassword)

        findViewById<View>(R.id.userLoginBtn).setOnClickListener(this@Login)
        findViewById<View>(R.id.loginBackBtn).setOnClickListener(this@Login)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.userLoginBtn -> {
                userLogin()

            }

            R.id.loginBackBtn -> {
                goBack()
            }
        }
    }

    private fun goBack() {
        startActivity(Intent(this@Login, MainActivity::class.java))
    }

    private fun userLogin() {
        val email: String = emailAddress.text.toString().trim()
        val pwd: String = password.text.toString().trim()

        val callback = object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val loginResponse: LoginResponse? = response.body()

                if (loginResponse != null) {
                    if (!loginResponse.isError) {
                        goToUserProfile()
                    } else {
                        Toast.makeText(this@Login, loginResponse.message, Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@Login, "An unknown error occurred.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@Login, t.message, Toast.LENGTH_LONG).show()
            }
        }

        val call: Call<LoginResponse> = RetrofitClient.getInstance().getApi()
            .userLogin(email, pwd)
        call.enqueue(callback)
    }

    private fun goToUserProfile() {
        startActivity(Intent(this@Login, UserActivity::class.java))
    }

}
