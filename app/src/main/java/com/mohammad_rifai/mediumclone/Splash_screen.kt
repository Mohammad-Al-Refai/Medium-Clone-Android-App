package com.mohammad_rifai.mediumclone

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import java.io.File
import java.util.jar.Manifest

class Splash_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE),1)



        if(isConnected()){
            object : CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                }
                override fun onFinish() {
                    var intent=Intent(this@Splash_screen,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.start()
        }else{

            var alert= AlertDialog.Builder(this)
            alert.setMessage("No Internet")
            alert.setCancelable(false)
            alert.setPositiveButton("Retry",{ dialogInterface: DialogInterface, i: Int ->
                Toast.makeText(this,"fuck you",Toast.LENGTH_LONG).show()
            })
            alert.show()
        }







    }
    fun isConnected():Boolean{
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }
}