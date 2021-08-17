package submission.andhiratobing.githubuser.view.activities.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import submission.andhiratobing.githubuser.MainActivity
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.databinding.ActivitySplashScreenBinding


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Splashscreen with lottie
        binding.apply {
            ivSplashScreen.setAnimation(R.raw.github_lottie)
            ivSplashScreen.playAnimation()
        }

        android.os.Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3500)
    }
}
