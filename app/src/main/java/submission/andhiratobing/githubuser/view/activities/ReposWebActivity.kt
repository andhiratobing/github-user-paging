package submission.andhiratobing.githubuser.view.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import submission.andhiratobing.githubuser.data.remote.responses.repos.ReposResponse
import submission.andhiratobing.githubuser.databinding.ActivityReposWebViewBinding
import submission.andhiratobing.githubuser.util.extension.hide
import submission.andhiratobing.githubuser.util.extension.show

@AndroidEntryPoint
class ReposWebActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReposWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReposWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataFromDetailUser()

        binding.ibBackToApp.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun getDataFromDetailUser() {
        val data = intent.getParcelableExtra<ReposResponse>(HTML_URL)
        binding.apply {
            progressBarForOpenWeb.show()
            binding.wvListReposToWeb.apply {
                data?.htmlUrl?.let {
                    loadUrl(data.htmlUrl)
                    Log.d("Test html url", data.htmlUrl)
                    settings.javaScriptEnabled = true
                    settings.builtInZoomControls = true
                    progressBarForOpenWeb.show()
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            binding.progressBarForOpenWeb.hide()
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val HTML_URL = "html_url"
    }

}