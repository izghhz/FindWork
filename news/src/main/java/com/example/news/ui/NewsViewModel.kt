package com.example.news.ui

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.*
import com.example.common.BaseApplication
import com.example.news.model.NewsListResponse
import com.example.news.network.Repository
import kotlinx.coroutines.launch

class NewsViewModel :ViewModel(){
    private val newsLiveData = MutableLiveData<NewsListResponse>()
    private val _pageLiveData = MutableLiveData<Int>()
    var pageLivData :LiveData<Int> = _pageLiveData

    private var page = 1
    private var word = ""


    val wNewsLiveData = Transformations.switchMap(newsLiveData) {
        if(page!=1){
            Log.d("MyTest", "$page: ")
            Repository.getNextNews(page)
        }else if(word.length>0){
            Log.d("MyTest", "word: ")
            Repository.getNewsByWord(word)
        }else
            Repository.getNowNews()

    }


    fun getNewsList(){
        newsLiveData.value = newsLiveData.value
    }
    fun getNextNewsList(){
        page++
//        _pageLiveData.value = page
        newsLiveData.value = newsLiveData.value

    }
    fun getNewsListByWord(word:String){
        this.word = word
        newsLiveData.value = newsLiveData.value
    }
    fun savaPage(){
        viewModelScope.launch {//在协程中进行数据的耗时操作
            sharedPreferences().edit {
                putInt("page",page)
            }
        }

    }
    fun getPage(){
        page = sharedPreferences().getInt("page", 0)
    }


    private fun sharedPreferences() =
        BaseApplication.context.getSharedPreferences("page_save", Context.MODE_PRIVATE)

}