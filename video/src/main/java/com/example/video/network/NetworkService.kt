package com.example.video.network


import com.example.common.BaseApplication
import com.example.video.model.DyListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    //这个api是抖音视频榜不支持添加参数，隔壁新闻的支持
    @GET("index?key=${BaseApplication.TOKEN}")
    fun getDylist(): Call<DyListResponse>
//    @GET("index?key=${BaseApplication.TOKEN}&num=50&page=1&word=北京")
//    fun getDylist(): Call<DyListResponse>
//
//    @GET("index?key=${BaseApplication.TOKEN}&num=50&page={page}")
//    fun getNextDylist( page: Int): Call<DyListResponse>
//
//    @GET("index?key=${BaseApplication.TOKEN}&num=50&word={word}")
//    fun getDylistByWord(word:String): Call<DyListResponse>
}