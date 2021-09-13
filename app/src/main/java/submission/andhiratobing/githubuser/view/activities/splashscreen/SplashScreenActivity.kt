package submission.andhiratobing.githubuser.view.activities.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import submission.andhiratobing.githubuser.MainActivity
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.databinding.ActivitySplashScreenBinding
import submission.andhiratobing.githubuser.util.Constants.Companion.DELAY_MILLISECOND


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launchWhenStarted {
            //Splashscreen with lottie
            binding.apply {
                ivSplashScreen.setAnimation(R.raw.github_lottie)
                ivSplashScreen.playAnimation()
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, DELAY_MILLISECOND)

    }
}
