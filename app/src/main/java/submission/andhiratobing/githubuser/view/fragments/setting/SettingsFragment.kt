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
import androidx.preference.SwitchPreference
import dagger.hilt.android.AndroidEntryPoint
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.reminders.ReminderReceiver
import submission.andhiratobing.githubuser.util.theme.ThemeProvider
import java.security.InvalidParameterException

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private val themeProvider by lazy { ThemeProvider(requireActivity()) }
    private val themePreference by lazy { findPreference<ListPreference>(getString(R.string.theme_preferences_key)) }
    private val reminderProvider by lazy { ReminderReceiver() }
    private val reminderPreference by lazy { findPreference<SwitchPreference>(getString(R.string.reminders_preferences_key)) }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

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
            getString(R.string.reminders_preferences_key) -> {
                setReminderPreference()
                true
            }


            else -> super.onPreferenceTreeClick(preferenceScreen)
        }
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


    private fun setReminderPreference() {
        reminderPreference?.setOnPreferenceChangeListener { _, newValue ->
            when (newValue) {
                true -> reminderProvider.setRepeatingReminder(
                        requireActivity(),
                        ReminderReceiver.Companion.TypeReminder.TYPE_REPEATING
                    )

                false ->
                    reminderProvider.cancelReminders(
                        requireActivity(),
                        ReminderReceiver.Companion.TypeReminder.TYPE_REPEATING
                    )

                else -> throw InvalidParameterException("Reminder not defined for $newValue")
            }
            true
        }

    }

}