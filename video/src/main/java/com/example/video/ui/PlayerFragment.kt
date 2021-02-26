package com.example.video.ui

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.video.R
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PlayerFragment(private val url :String) : Fragment() {
    private val  mediaPlayer = MediaPlayer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaPlayer.apply {
            setDataSource(url)
            setOnPreparedListener {
                progressBarH.max = mediaPlayer.duration
                progressBar2.visibility = View.INVISIBLE
                seekTo(1)
            }
            prepareAsync()
            progressBar2.visibility = View.VISIBLE

        }
        surfaceView.holder.addCallback(object:SurfaceHolder.Callback{
            override fun surfaceCreated(p0: SurfaceHolder) { }

            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
                mediaPlayer.setDisplay(p0)
                mediaPlayer.setScreenOnWhilePlaying(true)
            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {  }

        })
        lifecycleScope.launch {
            while (true){
                progressBarH.progress = mediaPlayer.currentPosition
                delay(500)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
        lifecycleScope.launch { //守护进程，第一个viewpager加载好时，mediaplayer不一定已经加载好了，所以上一行的start不一定生效
            while (!mediaPlayer.isPlaying){//如果视频没在播放，则让他播放，每0.5秒执行一次
                mediaPlayer.start()
                delay(500)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
    }
}