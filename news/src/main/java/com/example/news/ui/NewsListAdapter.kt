package com.example.apiuser.ui.toutiao

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.common.BaseApplication
import com.example.news.R
import com.example.news.model.NewsListResponse
import com.youth.banner.Banner
import com.youth.banner.loader.ImageLoader



class NewsListAdapter(var dataList:List<NewsListResponse.News>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
     val banner = 10
     val refresh = 20

    inner class mViewHolder0(view: View) : RecyclerView.ViewHolder(view) {
        val banner  = view.findViewById<Banner>(R.id.banner)
        val images = ArrayList<Int>().apply {
            add(R.drawable.bannerimg1)
            add(R.drawable.bannerimg2)
            add(R.drawable.bannerimg3)
            add(R.drawable.bannerimg4)
        }
    }
    inner class mViewHolder1(view: View) : RecyclerView.ViewHolder(view) {
        val tv_title = view.findViewById<TextView>(R.id.newsvh_title)
        val tv_content = view.findViewById<TextView>(R.id.newsvh_content)
        val tv_source = view.findViewById<TextView>(R.id.newsvh_source)
        val tv_ctime = view.findViewById<TextView>(R.id.newsvh_ctime)
        val iv_img1 = view.findViewById<ImageView>(R.id.newsvh_img1)

    }

    inner class mViewHolder2(view: View) : RecyclerView.ViewHolder(view) {
        val tv_title = view.findViewById<TextView>(R.id.newsvh_title)
        val tv_content = view.findViewById<TextView>(R.id.newsvh_content)
        val tv_source = view.findViewById<TextView>(R.id.newsvh_source)
        val tv_ctime = view.findViewById<TextView>(R.id.newsvh_ctime)
        val iv_img1 = view.findViewById<ImageView>(R.id.newsvh_img1)
        val iv_img2 = view.findViewById<ImageView>(R.id.newsvh_img2)
    }

    inner class mViewHolder3(view: View) : RecyclerView.ViewHolder(view) {
        val tv_title = view.findViewById<TextView>(R.id.newsvh_title)
        val tv_content = view.findViewById<TextView>(R.id.newsvh_content)
        val tv_source = view.findViewById<TextView>(R.id.newsvh_source)
        val tv_ctime = view.findViewById<TextView>(R.id.newsvh_ctime)
        val iv_img1 = view.findViewById<ImageView>(R.id.newsvh_img1)
        val iv_img2 = view.findViewById<ImageView>(R.id.newsvh_img2)
        val iv_img3 = view.findViewById<ImageView>(R.id.newsvh_img3)
    }
    inner class mViewHolder4(view: View) : RecyclerView.ViewHolder(view) {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            refresh ->{
                val v =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.newsviewholder4, parent, false)
                return mViewHolder4(v)
            }
            banner -> {
                val v =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.newsviewholder0, parent, false)
                return mViewHolder0(v)
            }
            1 -> {
                val v =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.newsviewholder1, parent, false)
                return mViewHolder1(v)
            }
            2 -> {
                val v =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.newsviewholder2, parent, false)
                return mViewHolder2(v)
            }
            else -> {
                val v =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.newsviewholder3, parent, false)
                return mViewHolder3(v)
            }
        }

    }
    //创建点击监听器接口
    private var mOnItemClickListener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(position: Int,itemType:Int,url:String)

    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = onItemClickListener
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val listPosition = if(position<11)position-1 else position-2
        when (holder.itemViewType) {
            refresh -> {
                val myholder: mViewHolder4 = holder as mViewHolder4
                myholder.itemView.findViewById<ConstraintLayout>(R.id.refrelayout)
                    .setOnClickListener {
                        mOnItemClickListener?.onItemClick( holder.itemViewType,position,"https://www.baidu.com")
                        notifyDataSetChanged()
                    }
            }
            banner -> {
                val myholder: mViewHolder0 = holder as mViewHolder0
                myholder.banner.apply {
                    setImageLoader(object : ImageLoader() {
                        override fun displayImage(
                            context: Context?,
                            path: Any?,
                            imageView: ImageView?
                        ) {
                            if (context != null) {
                                Glide.with(context).load(path).into(imageView!!)
                            }
                        }

                    })
                    setImages(myholder.images)
                    start()
                }
            }
            1 -> {
                val myholder: mViewHolder1 = holder as mViewHolder1
                myholder.tv_title.text = dataList.get(listPosition).title
                myholder.tv_content.text = dataList.get(listPosition).description
                myholder.tv_ctime.text = dataList.get(listPosition).ctime
                myholder.tv_source.text = dataList.get(listPosition).source
                Glide.with(BaseApplication.context)
                    .load(dataList.get(listPosition).picUrl)
                    .placeholder(R.drawable.ic_baseline_wifi_off_24)
                    .into(myholder.iv_img1)
                holder.itemView.setOnClickListener {
                    mOnItemClickListener?.onItemClick( holder.itemViewType,position,dataList[listPosition].url)
                }
            }
            2 -> {
                val myholder: mViewHolder2 = holder as mViewHolder2
                myholder.tv_title.text = dataList.get(listPosition).title
                myholder.tv_content.text = dataList.get(listPosition).description
                myholder.tv_ctime.text = dataList.get(listPosition).ctime
                myholder.tv_source.text = dataList.get(listPosition).source
                Glide.with(BaseApplication.context)
                    .load(dataList.get(listPosition).picUrl)
                    .placeholder(R.drawable.ic_baseline_wifi_off_24)
                    .into(myholder.iv_img1)
                Glide.with(BaseApplication.context)
                    .load(dataList.get(listPosition).picUrl)
                    .placeholder(R.drawable.ic_baseline_wifi_off_24)
                    .into(myholder.iv_img2)
                holder.itemView.setOnClickListener {
                    mOnItemClickListener?.onItemClick( holder.itemViewType,position,dataList[listPosition].url)
                }
            }
            else -> {
                val myholder: mViewHolder3 = holder as mViewHolder3
                myholder.tv_title.text = dataList.get(listPosition).title
                myholder.tv_content.text = dataList.get(listPosition).description
                myholder.tv_ctime.text = dataList.get(listPosition).ctime
                myholder.tv_source.text = dataList.get(listPosition).source
                Glide.with(BaseApplication.context)
                    .load(dataList.get(listPosition).picUrl)
                    .placeholder(R.drawable.ic_baseline_wifi_off_24)
                    .into(myholder.iv_img1)
                Glide.with(BaseApplication.context)
                    .load(dataList.get(listPosition).picUrl)
                    .placeholder(R.drawable.ic_baseline_wifi_off_24)
                    .into(myholder.iv_img2)
                Glide.with(BaseApplication.context)
                    .load(dataList.get(listPosition).picUrl)
                    .placeholder(R.drawable.ic_baseline_wifi_off_24)
                    .into(myholder.iv_img3)
                holder.itemView.setOnClickListener {
                    mOnItemClickListener?.onItemClick( holder.itemViewType,position,dataList[listPosition].url)
                }
            }
        }
    }

    override fun getItemCount(): Int {
            return dataList.size+2
        //轮播图+十条新闻+刷新view
    }
    override fun getItemViewType(position: Int): Int {
        if (position ==0){
            return banner
        }
        else if(position ==11||(dataList.size<10&&position == dataList.size+2-1)){//一次刷新十条，第十一位用来刷新,或者当前列表没有数据时
            return refresh
        }
        else
        return position % 3
    }

}