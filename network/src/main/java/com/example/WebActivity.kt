package com.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.network.R


@Route(path = "/network/web")
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
            webChromeClient = object :WebChromeClient(){
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    if (newProgress == 100) this@WebActivity.findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
                }
            }



          loadUrl(urlKey)

        }

    }
}