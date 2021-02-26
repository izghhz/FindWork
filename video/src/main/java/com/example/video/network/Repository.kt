package com.example.video.network

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {
    //获取当前视频
    fun getNowVideo() = fire(Dispatchers.IO) {
        coroutineScope {
            val dyVideoResponse = async { Serviceimp.getNowVideo()}
            val dyPreview = dyVideoResponse.await()
            if (dyPreview.code ==200) {
                val videoList = dyPreview
                //Log.d("Mytest", "refresh: ${videoList}")
                Result.success(videoList)
            } else {
                Result.failure(RuntimeException("response status is ${dyPreview}"))
            }
        }
    }
/*    //获取下一页视频
    fun getNextVideo(page:Int) = fire(Dispatchers.IO) {
        coroutineScope {
            val dyVideoResponse = async { Serviceimp.getNextVideo(page)}
            val dyPreview = dyVideoResponse.await()
            if (dyPreview.code ==200) {
                val videoList = dyPreview
                //Log.d("Mytest", "refresh: ${videoList}")
                Result.success(videoList)
            } else {
                Result.failure(RuntimeException("response status is ${dyPreview}"))
            }
        }
    }
    //搜索视频关键字
    fun getVideoByWord(word:String) = fire(Dispatchers.IO) {
        coroutineScope {
            val dyVideoResponse = async { Serviceimp.getVideoByWord(word)}
            val dyPreview = dyVideoResponse.await()
            if (dyPreview.code ==200) {
                val videoList = dyPreview
                //Log.d("Mytest", "refresh: ${videoList}")
                Result.success(videoList)
            } else {
                Result.failure(RuntimeException("response status is ${dyPreview}"))
            }
        }
    }*/
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