package com.example.news.network

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.liveData
import com.example.common.BaseApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {
    //获取当前新闻
    fun getNowNews() = fire(Dispatchers.IO) {
        coroutineScope {
            val newsResponse = async { Serviceimp.getNowNews()}
            val newsPreview = newsResponse.await()
            if (newsPreview.code ==200) {
                Result.success(newsPreview)
            }else if(newsPreview.code ==150){//到达每日api使用次数上限

                Result.failure(RuntimeException("response status is ${newsPreview}"))
            }
            else {
                Result.failure(RuntimeException("response status is ${newsPreview}"))
            }
        }
    }

    fun getNextNews(page:Int) = fire(Dispatchers.IO) {
        coroutineScope {
            val newsResponse = async { Serviceimp.getNextNews(page)}
            val newsPreview = newsResponse.await()
            if (newsPreview.code ==200) {
                Result.success(newsPreview)
            }else if(newsPreview.code ==150){//到达每日api使用次数上限

                Result.failure(RuntimeException("response status is ${newsPreview}"))
            }
            else {
                Result.failure(RuntimeException("response status is ${newsPreview}"))
            }
        }
    }

    fun getNewsByWord(word:String) = fire(Dispatchers.IO) {
        coroutineScope {
            val newsResponse = async { Serviceimp.getNewsByWord(word)}
            val newsPreview = newsResponse.await()
            if (newsPreview.code ==200) {
                Log.d("Mytest", "getNowNews: ${newsPreview.newslist}")
                Result.success(newsPreview)
            }else if(newsPreview.code ==150){//到达每日api使用次数上限

                Result.failure(RuntimeException("response status is ${newsPreview}"))
            }
            else {
                Result.failure(RuntimeException("response status is ${newsPreview}"))
            }
        }
    }


    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            //suspend关键字为了保证lambda表达式拥有挂起函数的上下文
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)//相当于setValue，但当前没有LiveData对象，所以也取不到返回值
        }
}