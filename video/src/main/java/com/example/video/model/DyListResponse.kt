package com.example.video.model

import com.google.gson.annotations.SerializedName

data class DyListResponse(val code: Int, val message: String, @SerializedName("newslist")val videolist: List<Video>) {

   data class Video(
        val hotindex: Int,
        val createtime: Int,
        val duration: Int,
        val playaddr: String,
        val coverurl: String,
        val title: String,
        val shareurl: String,
        val author: String,
        val signature: String,
        val avatar: String,
    )

}