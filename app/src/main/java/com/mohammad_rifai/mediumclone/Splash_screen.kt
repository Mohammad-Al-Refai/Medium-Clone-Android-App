package com.mohammad_rifai.mediumclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Splash_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)


        var intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}