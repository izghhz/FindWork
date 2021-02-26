package com.example.video.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.video.network.Repository
import com.example.video.model.DyListResponse

class VideoViewModel:ViewModel() {
    private val videoLiveData = MutableLiveData<DyListResponse>()
/*    private var page = 1
    private var word = ""*/
    private var videourls = ArrayList<String>()


    val wVideoLiveData = Transformations.switchMap(videoLiveData) {
/*        if(page!=1){
            Repository.getNextVideo(page)
        }else if(word.length>0){
           Repository.getVideoByWord(word)
        }else*/
            Repository.getNowVideo()


    }

    fun getVideoList(){
        videoLiveData.value = videoLiveData.value
    }
/*    fun getNextVideoList(){
        this.page++
        videoLiveData.value = videoLiveData.value

    }
    fun getVideoListByWord(word:String){
        this.word = word
        videoLiveData.value = videoLiveData.value
    }*/
}