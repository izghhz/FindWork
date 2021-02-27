package com.example.findwork

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.BaseActivity
import com.example.video.ui.FooFragment
import kotlinx.android.synthetic.main.about_icon_layout.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.gallery_icon_layout.*
import kotlinx.android.synthetic.main.home_icon_layout.*
import kotlinx.android.synthetic.main.video_icon_layout.*

@Route(path = "/home/main")
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val map = mapOf(
            0 to homeMotionLayout,
            1 to videoMotionLayout,
            2 to galleryMotionLayout,
            3 to aboutMotionLayout
        )
        homeMotionLayout.apply {
            progress = 0f//设置动画进度，整个过程为0-1
            //transitionToEnd()//播放动画
            setOnClickListener {
                if (App.nowTab != 0) {
                    App.nowTab = 0
                    viewpager.setCurrentItem(0, false)
                }
            }
        }
        videoMotionLayout.apply {
            progress = 0f
            //transitionToEnd()
            setOnClickListener {
                if (App.nowTab != 1) {
                    App.nowTab = 1
                    viewpager.setCurrentItem(1, false)
                }
            }
        }
        galleryMotionLayout.apply {
            progress = 0f
            //transitionToEnd()
            setOnClickListener {
                if (App.nowTab != 2) {
                    App.nowTab = 2
                    viewpager.setCurrentItem(2, false)
                }
            }
        }
        aboutMotionLayout.apply {
            progress = 0f
            //transitionToEnd()
            setOnClickListener {
                if (App.nowTab != 3) {
                    App.nowTab = 3
                    viewpager.setCurrentItem(3, false)
                }
            }
        }

        viewpager.apply {
            isUserInputEnabled = false//禁止左右划动
            adapter = object : FragmentStateAdapter(this@MainActivity) {
                override fun getItemCount(): Int = 4

                override fun createFragment(position: Int): Fragment = when (position) {
                    0 -> ARouter.getInstance().build("/news/show").navigation() as Fragment
                    1 -> ARouter.getInstance().build("/video/player").navigation() as Fragment
                    else -> FooFragment()
                }
            }
            setCurrentItem(App.nowTab, false)//确保nowTab与当前页面序号一致
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {//页面发生变化时的监听
                override fun onPageSelected(position: Int) {
                    map.values.forEach { it.transitionToStart() }//先全部回到初始状态
                    map[position]?.transitionToEnd()//当前页面对应按钮的进入结束状态
                }
            })
        }
    }

}



