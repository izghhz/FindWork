package com.example.video.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.common.BaseActivity
import com.example.video.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_video.*

@Route(path = "/video/main")
class VideoActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

                mainViewPager.apply {
                    adapter = object : FragmentStateAdapter(this@VideoActivity){
                        override fun getItemCount(): Int =3

                        override fun createFragment(position: Int): Fragment = when(position){
                            1 -> VedioFragment()
                            else -> FooFragment()
                        }
                    }
                    setCurrentItem(1,false)
                }
                TabLayoutMediator(tabLayout,mainViewPager){ tab: TabLayout.Tab, i: Int ->
                    tab.text = when(i){
                        1 -> "vedio"
                        else -> "foo"
                    }
                }.attach()



    }
}