package com.mohammad_rifai.mediumclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import app_networking.Connector
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        var connector = Connector(this, packageName)

        btn_register.setOnClickListener {
            var view = it
            var user_name = register_username.text.toString()
            var email = register_email.text.toString()
            var password = register_password.text.toString()

            connector.register(user_name, email, password) {
                if (it[0] as Boolean) {
                    Snackbar.make(view, it[1].toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                } else {
                    Snackbar.make(view, it[1].toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
            }
            }
        btn_login.setOnClickListener {
            var intent=Intent(this,Login::class.java)
            startActivity(intent)
        }
        }
    }