package com.mohammad_rifai.mediumclone.ui.fovorite

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

class ProfileFragment : Fragment() {

    private lateinit var slideshowViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.activity_profile, container, false)





        var anim= AnimationUtils.loadAnimation(root.context,R.anim.fade_in_text)
        root.startAnimation(anim)
        return root
    }
}