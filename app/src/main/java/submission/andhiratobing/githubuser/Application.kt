package submission.andhiratobing.githubuser

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import submission.andhiratobing.githubuser.util.theme.ThemeProvider

@HiltAndroidApp
class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        val theme = ThemeProvider(this).getThemeFromPreferences()
        AppCompatDelegate.setDefaultNightMode(theme)
    }
}