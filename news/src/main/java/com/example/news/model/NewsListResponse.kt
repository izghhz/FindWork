package com.example.news.model

data class NewsListResponse(val code: Int, val message: String, val newslist: List<News>) {

   data class News(
        val ctime: String,
        val title: String,
        val description: String,
        val picUrl: String,
        val url: String,
        val source: String
    )

}