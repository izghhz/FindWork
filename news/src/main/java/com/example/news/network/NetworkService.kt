package com.example.news.network


import com.example.common.BaseApplication
import com.example.news.model.NewsListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("index?key=${BaseApplication.TOKEN}")
    fun getNewslist(): Call<NewsListResponse>

    @GET("index?key=${BaseApplication.TOKEN}&num=10")
    fun getNextNewslist(@Query("page")page: Int): Call<NewsListResponse>

    @GET("index?key=${BaseApplication.TOKEN}&word={word}&num=10")
    fun getNewslistByWord(@Path("word")word:String): Call<NewsListResponse>
}