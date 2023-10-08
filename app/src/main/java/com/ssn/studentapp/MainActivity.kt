
package com.ssn.studentapp


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.registerBtn).setOnClickListener(this@MainActivity)
        findViewById<View>(R.id.loginBtn).setOnClickListener(this@MainActivity)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.registerBtn -> userSignUp()
            R.id.loginBtn -> userLogin()
        }
    }

    private fun userLogin() {
        startActivity(Intent(this@MainActivity, Login::class.java))
    }

    private fun userSignUp() {
        startActivity(Intent(this@MainActivity, Register::class.java))
    }
}
