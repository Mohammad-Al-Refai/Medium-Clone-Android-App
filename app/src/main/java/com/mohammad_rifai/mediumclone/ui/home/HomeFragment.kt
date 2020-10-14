package com.mohammad_rifai.mediumclone.ui.home

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AbsListView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app_networking.Connector
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.mohammad_rifai.mediumclone.CommentViewActivity
import com.mohammad_rifai.mediumclone.PostAdapter
import com.mohammad_rifai.mediumclone.PostViewActivity
import com.mohammad_rifai.mediumclone.R
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.json.JSONArray
import org.json.JSONObject


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            ///// coding here for on start avrivity
            //id,title,details,from,image,seen,date
            var CONNECTOR=Connector(root.context)
            var data= arrayListOf<Array<String>>()
            CONNECTOR.get_data {
                Log.i("CCCCCCCC",it.toString())
                var items=JSONArray(it.toString())
                for (i in 0..items.length()-1){

                    data.add(
                        arrayOf(
                       JSONObject(items[i].toString())["id"].toString(),
                       JSONObject(items[i].toString())["title"].toString(),
                       JSONObject(items[i].toString())["details"].toString(),
                       JSONObject(items[i].toString())["author_name"].toString(),
                       JSONObject(items[i].toString())["image"].toString(),
                       "0",
                       "0"
                    )

                    )
                }
            }

            var progress=ProgressDialog(root.context)
            progress.setMessage("Loading...")
            root.list_post.setOnScrollListener(object : AbsListView.OnScrollListener {
                private var currentVisibleItemCount = 0
                private var currentScrollState = 0
                private var currentFirstVisibleItem = 0
                private var totalItem = 0
                override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                    // TODO Auto-generated method stub
                    currentScrollState = scrollState
                    isScrollCompleted
                }

                override fun onScroll(
                    view: AbsListView, firstVisibleItem: Int,
                    visibleItemCount: Int, totalItemCount: Int
                ) {

                    currentFirstVisibleItem = firstVisibleItem
                    currentVisibleItemCount = visibleItemCount
                    totalItem = totalItemCount
                }

                private val isScrollCompleted: Unit
                    private get() {
                        if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                            && currentScrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        ) {

                          progress.show()
                        }else{
                         progress.dismiss()
                        }
                    }
            })

            root.list_post.adapter=PostAdapter(root.context,data)

            PostAdapter(root.context,data).notifyDataSetChanged()

            var anim=AnimationUtils.loadAnimation(root.context,R.anim.fade_in)
            root.list_post.startAnimation(anim)
            root.list_post.setOnItemClickListener { parent, view, position, id ->
                var intent=Intent(root.context,PostViewActivity::class.java)
                intent.putExtra("id",data[position][0])
                intent.putExtra("title",data[position][1])
                startActivity(intent)

            }

        })

        return root
    }
}
