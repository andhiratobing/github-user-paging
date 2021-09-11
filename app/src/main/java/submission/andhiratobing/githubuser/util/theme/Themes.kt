package submission.andhiratobing.githubuser.util.theme

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import submission.andhiratobing.githubuser.R

enum class Themes(
    val themeSelected: Int,
    @StringRes val themeNameRes: Int
) {
    FOLLOW_SYSTEM(
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
        R.string.system_theme_description
    ),
    DARK(
        AppCompatDelegate.MODE_NIGHT_YES,
        R.string.dark_theme_description
    ),
    LIGHT(
        AppCompatDelegate.MODE_NIGHT_NO,
        R.string.light_theme_description
    );

    companion object {
        val THEMES_ARRAY = arrayOf(
            FOLLOW_SYSTEM,
            DARK,
            LIGHT
        )
    }
}