package submission.andhiratobing.githubuser.view.fragments.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import dagger.hilt.android.AndroidEntryPoint
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.util.theme.ThemeProvider

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private val themeProvider by lazy { ThemeProvider(requireActivity()) }
    private val themePreference by lazy { findPreference<ListPreference>(getString(R.string.theme_preferences_key)) }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        return when (preference?.key) {
            getString(R.string.language_preferences_key) -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
                true
            }
            getString(R.string.theme_preferences_key) -> {
                setThemePreference()
                true
            }
            else -> super.onPreferenceTreeClick(preferenceScreen)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val defaultView = super.onCreateView(inflater, container, savedInstanceState) as View
        val newLayout =
            inflater.inflate(R.layout.layout_toolbar_settings, container, false) as ViewGroup
        newLayout.addView(defaultView)
        defaultView.setPaddingRelative(0, 120, 0, 0)
        return newLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbarSettings = view.findViewById<Toolbar>(R.id.toolbarSettings)
        toolbarSettings.apply {
            title = getString(R.string.settings)
        }
    }

    private fun setThemePreference() {
        themePreference?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                if (newValue is String) {
                    val theme = themeProvider.getTheme(newValue)
                    AppCompatDelegate.setDefaultNightMode(theme)
                }
                true
            }
        themePreference?.summaryProvider =
            Preference.SummaryProvider<ListPreference> { preference ->
                themeProvider.getThemeDescriptionForPreference(preference.value)
            }
    }
}