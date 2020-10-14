package com.mohammad_rifai.mediumclone

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.BaseAdapter
import android.widget.TextView
import app_networking.Connector
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.post.view.*
var API="http://192.168.8.105:3000/"
class PostAdapter :BaseAdapter{
    var context:Context?=null
    var Data= arrayListOf<Array<String>>()
    constructor(context: Context,data:ArrayList<Array<String>>){
    this.context=context
     this.Data=data
    }
    //id,title,details,from,image,seen
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
    var inflate=context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var view=inflate.inflate(R.layout.post,null)
        view.post_id.text=Data[position][0]
        view.post_title.text=Data[position][1]

        if (Data[position][2].length>=200){
            view.post_details.text=Html.fromHtml(Data[position][2].substring(0,180)).toString()+"..."
        }else{
            view.post_details.text=Html.fromHtml(Data[position][2])
        }
        view.post_from.text=Data[position][3]
        Picasso.get().load(API+Data[position][4]).into(view.post_image)
        Log.i("SSSSSSS",API+Data[position][4])
       view.post_seen.text= Data[position][5]
        view.post_date.text=Data[position][6]
        val anim= AnimationUtils.loadAnimation(context,R.anim.fade_in)
        view.startAnimation(anim)
     return view
    }

    override fun getItem(position: Int): Any {
      return  Data[position].toString()
    }

    override fun getItemId(position: Int): Long {
       return  position.toLong()
    }

    override fun getCount(): Int {
     return Data.size
    }


}
