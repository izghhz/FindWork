package com.example.news.network

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object Serviceimp {

    val neswService = ServiceCreator.create(NetworkService::class.java)
    suspend fun getNowNews() = neswService.getNewslist().await()
    suspend fun getNextNews(page:Int) = neswService.getNextNewslist(page).await()
    suspend fun getNewsByWord(word:String) = neswService.getNewslistByWord(word).await()
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                    Log.d("Mytest", "refresh: 失败+$t")
                }
            })
        }
    }
}