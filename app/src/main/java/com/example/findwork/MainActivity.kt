package com.example.findwork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.BaseActivity
import com.example.video.ui.FooFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = "/home/main")
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewPager.apply {
            adapter = object : FragmentStateAdapter(this@MainActivity){
                override fun getItemCount(): Int =3

                override fun createFragment(position: Int): Fragment = when(position){
                    0 -> ARouter.getInstance().build("/news/show").navigation() as Fragment
                   //0 -> FooFragment()
                    1 -> ARouter.getInstance().build("/video/player").navigation() as Fragment//路由到实例加载
                    else -> FooFragment()
                }
            }
            setCurrentItem(1,false)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == this@apply.adapter?.itemCount) {
                        Toast.makeText(this@MainActivity, "没有更多了", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        TabLayoutMediator(tabLayout,mainViewPager){ tab: TabLayout.Tab, i: Int ->
            tab.text = when(i){
                0 -> "资讯"
                1 -> "视频"
                else -> "图库"
            }
        }.attach()

    }
}