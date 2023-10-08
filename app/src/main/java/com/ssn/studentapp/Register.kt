
package com.ssn.studentapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ssn.studentapp.api.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class Register : AppCompatActivity(), OnClickListener {

    private lateinit var firstNameText: EditText
    private lateinit var lastNameText: EditText
    private lateinit var emailAddressText: EditText
    private lateinit var passwordText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firstNameText = findViewById(R.id.editTextFirstName)
        lastNameText = findViewById(R.id.editTextLastName)
        emailAddressText = findViewById(R.id.editTextEmail)
        passwordText = findViewById(R.id.editTextPassword)

        findViewById<View>(R.id.registerUserBtn).setOnClickListener(this@Register)
        findViewById<View>(R.id.registerBackBtn).setOnClickListener(this@Register)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.registerBackBtn ->
                goBack()

            R.id.registerUserBtn ->
                registerUser()

        }
    }

    private fun goBack() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun registerUser() {
        val firstName = firstNameText.text.toString().trim()
        val lastName = lastNameText.text.toString().trim()
        val email = emailAddressText.text.toString().trim()
        val pwd = passwordText.text.toString().trim()

        val callback = object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    val body: String? = response.body()?.string()
                    Toast.makeText(this@Register, body, Toast.LENGTH_LONG).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@Register, t.message, Toast.LENGTH_LONG).show()
            }
        }


        val call: Call<ResponseBody> = RetrofitClient.getInstance().getApi()
            .createUser(firstName, lastName, email, pwd)
        call.enqueue(callback)
    }
}
