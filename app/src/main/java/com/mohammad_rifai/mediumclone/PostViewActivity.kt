package com.mohammad_rifai.mediumclone

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigator
import app_networking.Connector
import com.squareup.picasso.Picasso
import org.json.JSONObject

class PostViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_view)
        setSupportActionBar(findViewById(R.id.toolbar))
        val anim_fap= AnimationUtils.loadAnimation(this,R.anim.anim_fap)
        findViewById<FloatingActionButton>(R.id.add_fovorite).startAnimation(anim_fap)
        findViewById<FloatingActionButton>(R.id.add_clap).startAnimation(anim_fap)
        findViewById<FloatingActionButton>(R.id.add_comment).startAnimation(anim_fap)
        var CONNECTOR=Connector(this)
        var DATA_ID= intent.extras?.get("id") as String
        val anim=AnimationUtils.loadAnimation(this,R.anim.fade_in)
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = intent.extras?.get("title").toString()
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        findViewById<FloatingActionButton>(R.id.add_fovorite).setOnClickListener { view ->
            Snackbar.make(view, "added to Fovorite !", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        findViewById<FloatingActionButton>(R.id.add_comment).setOnClickListener { view ->
           var intent=Intent(this,CommentViewActivity::class.java)
            startActivity(intent)
        }
        Log.i("SSSSSSSSSSS",DATA_ID)
        CONNECTOR.get_data_details(DATA_ID){
            Log.i("SSSSSSSSSS",it.toString())
            var data=JSONObject(it.toString())
            val post_text=findViewById<TextView>(R.id.post_view_text)
            post_text.startAnimation(anim)
            val image=findViewById<ImageView>(R.id.post_view_image)
            Picasso.get().load( CONNECTOR.API+data["image"].toString()).into(image)
            post_text.text=Html.fromHtml(data["code"].toString())
            val anim= AnimationUtils.loadAnimation(this,R.anim.fade_in_text)
            image.startAnimation(anim)
        }


    }
}