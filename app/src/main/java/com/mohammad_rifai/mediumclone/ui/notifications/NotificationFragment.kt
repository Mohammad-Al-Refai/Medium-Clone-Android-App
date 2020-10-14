package com.mohammad_rifai.mediumclone.ui.notifications

import android.app.Notification
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mohammad_rifai.mediumclone.R

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: NotificatinViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProviders.of(this).get(NotificatinViewModel::class.java)
        val root = inflater.inflate(R.layout.notificatin_activity, container, false)



        var anim= AnimationUtils.loadAnimation(root.context,R.anim.fade_in_text)
        root.startAnimation(anim)
        return root
    }
}