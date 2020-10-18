package com.mohammad_rifai.mediumclone

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import app_networking.Activation
import app_networking.Connector
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val active=Activation(this,packageName)

        val fab: FloatingActionButton = findViewById(R.id.reload)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).setBackgroundTint(Color.parseColor("#FF4CAF50")).setTextColor(Color.WHITE) .show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE),1)


        val CONNECTOR=Connector(this,packageName)
        val progress= ProgressDialog(this)
        progress.setMessage("Loading...")
        progress.show()
        active.IsLogging {
            val result=it as Array<*>
            if(result[0] as Boolean){
                Toast.makeText(this,result[1].toString(),Toast.LENGTH_LONG).show()
                val data=JSONObject(result[2].toString())
                navView.getHeaderView(0).findViewById<TextView>(R.id.user_name).text=data.get("name").toString()
                navView.getHeaderView(0).findViewById<TextView>(R.id.user_email).text=data.get("email").toString()
                Log.i("SSSSSSSSSSSSSSSS",CONNECTOR.API+data.get("image").toString())
                progress.dismiss()

                if(data.get("image") == false){
                    Toast.makeText(this,"not set image",Toast.LENGTH_LONG).show()
                }else{
                    Picasso.get().load(CONNECTOR.API+data.get("image").toString()).into( navView.getHeaderView(0).findViewById<ImageView>(R.id.profile_image_nav))

                }
            }else{
               progress.dismiss()

            }
        }

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_notificarions, R.id.nav_fovorite,R.id.nav_profile
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}

