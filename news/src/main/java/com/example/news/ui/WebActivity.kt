package com.example.news.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.news.R

@Route(path = "/news/web")
class WebActivity : AppCompatActivity() {
    @JvmField
    @Autowired
    var urlKey:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        ARouter.getInstance().inject(this)
        findViewById<WebView>(R.id.webView).apply {
            settings.javaScriptEnabled=true
            settings.domStorageEnabled = true
            settings.loadsImagesAutomatically= true
            webViewClient= WebViewClient()

          loadUrl(urlKey)

        }

    }
}