package com.mohammad_rifai.mediumclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import app_networking.Connector
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var connector = Connector(this, packageName)
        login_submit.setOnClickListener {
            var email = login_email.text.toString()
            var password = login_password.text.toString()
            connector.login(email, password) {
                if (it[0] as Boolean) {
                  Toast.makeText(this,"done",Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this,"error",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}