package com.example.news.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.example.apiuser.ui.toutiao.NewsListAdapter
import com.example.apiuser.ui.toutiao.SpacesItemDecoration
import com.example.common.BaseApplication
import com.example.news.R
import com.example.news.model.NewsListResponse
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_toutiao.*
import kotlinx.android.synthetic.main.fragment_toutiao.view.*
import java.util.*

@Route(path = ("/news/show"))
class ToutiaoFragment : Fragment() {
    private var myList = mutableListOf<NewsListResponse.News>()
    private lateinit var viewModel: NewsViewModel
    private lateinit var madapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_toutiao, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // (activity as AppCompatActivity).setSupportActionBar(toolbar)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(NewsViewModel::class.java)
        viewModel.getNewsList()
        madapter = NewsListAdapter(myList)
        setAdapter(madapter)
        floatingActionButton.hide()
        floatingActionButton.setOnClickListener {
            smoothMoveToPosition(rv_toutiao, 0)
        }
        srl_refresh.apply {
            setColorSchemeResources(R.color.purple_200)
            setOnRefreshListener {
                viewModel.getNextNewsList()
            }
        }
        rv_toutiao.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val manager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = manager.findFirstVisibleItemPosition()
                if (mShouldScroll && RecyclerView.SCROLL_STATE_IDLE == newState) {
                    mShouldScroll = false
                    smoothMoveToPosition(rv_toutiao, mToPosition)
                }
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    if (firstVisibleItemPosition == 0) {//判断是否滚动超过一屏
                        floatingActionButton.hide()
                    } else {
                        floatingActionButton.show()
                    }
                }

            }
        })
        viewModel.wNewsLiveData.observe(viewLifecycleOwner, Observer {

            //rv_toutiao.scrollToPosition(0)
            val newsListResponse = it.getOrNull()
            newsListResponse?.let {
                progressBar.visibility = View.INVISIBLE
                val newList = it.newslist
                val oldList = madapter.dataList

                val nextList = mutableListOf<NewsListResponse.News>()
                nextList.addAll(newList)
                nextList.addAll(oldList)
                madapter.dataList = nextList
                madapter.notifyDataSetChanged()

                smoothMoveToPosition(rv_toutiao, 0)
            }
            viewModel.savaPage()
            srl_refresh.isRefreshing = false
        })


    }


    //目标项是否在最后一个可见项之后
    private var mShouldScroll = false

    //记录目标项位置
    private var mToPosition = 0

    /**
     * 滑动到指定位置
     */
    private fun smoothMoveToPosition(mRecyclerView: RecyclerView, position: Int) {
        // 第一个可见位置
        val firstItem: Int = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0))
        // 最后一个可见位置
        val lastItem: Int =
            mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1))
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position)
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后
            val movePosition = position - firstItem
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                val top: Int = mRecyclerView.getChildAt(movePosition).getTop()
                mRecyclerView.smoothScrollBy(0, top)
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position)
            mToPosition = position
            mShouldScroll = true
        }
    }

    private fun setAdapter(adapter: NewsListAdapter) {
        rv_toutiao.apply {
            layoutManager = LinearLayoutManager(BaseApplication.context)
            adapter.setOnItemClickListener(object : NewsListAdapter.OnItemClickListener {


                override fun onItemClick(position: Int, itemType: Int, url: String) {
                    if (itemType == adapter.banner) {
                        Log.d("Mytest", "onItemClick: $itemType")
                    } else if (itemType == adapter.refresh) {
                        viewModel.getNextNewsList()
                        Log.d("Mytest", "onItemClick: $itemType")
                    } else {
                        ARouter.getInstance().build("/network/web")
                            .withString("urlKey", url)
                            .navigation()
                        Log.d("Mytest", "onItemClick: $itemType")
                    }
                }

            })
            this.setAdapter(adapter)

            addItemDecoration(SpacesItemDecoration(8))
        }
    }

//    private suspend fun savePhoto() {
//        withContext(Dispatchers.IO) {
//            val holder =
//                rv_toutiao.findViewHolderForAdapterPosition(rv_toutiao.g)
//                        as PagerPhotoViewHolder
//            val bitmap = holder.itemView.pagerPhoto.drawable.toBitmap()
//
//            val saveUri = requireContext().contentResolver.insert(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                ContentValues()
//            )?: kotlin.run {
//                Toast.makeText(requireContext(), "存储失败", Toast.LENGTH_SHORT).show()
//                return@withContext
//            }
//            requireContext().contentResolver.openOutputStream(saveUri).use {
//                if (bitmap.compress(Bitmap.CompressFormat.JPEG,90,it)) {
//                    MainScope().launch { Toast.makeText(requireContext(), "存储成功", Toast.LENGTH_SHORT).show() }
//                } else {
//                    MainScope().launch { Toast.makeText(requireContext(), "存储失败", Toast.LENGTH_SHORT).show() }
//                }
//            }
//        }
//    }


}