package com.example.video.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.common.BaseApplication
import com.example.video.R
import com.github.easyguide.EasyGuideManager
import com.github.easyguide.layer.CommonGuideLayer
import com.github.easyguide.layer.Location
import kotlinx.android.synthetic.main.activity_video.*
import kotlinx.android.synthetic.main.fragment_vedio.*


@Route(path = "/video/player")
class VedioFragment() : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vedio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(VideoViewModel::class.java)
        viewModel.getVideoList()
        viewModel.wVideoLiveData.observe(this, Observer {
            val videoListResponse = it.getOrNull()
            videoListResponse?.let {

                playerViewPgaer.apply {
                    adapter = object : FragmentStateAdapter(this@VedioFragment) {
                        override fun getItemCount(): Int = it.videolist.size

                        override fun createFragment(position: Int): Fragment =
                            PlayerFragment(it.videolist.get(position).playaddr)



                    }
                    offscreenPageLimit = 5
                }
            }
        })






    }
}