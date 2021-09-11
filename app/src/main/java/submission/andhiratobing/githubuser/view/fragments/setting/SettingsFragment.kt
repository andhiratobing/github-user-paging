package submission.andhiratobing.githubuser.view.fragments.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.databinding.FragmentSettingsBinding
import submission.andhiratobing.githubuser.reminders.ReminderReceiver
import submission.andhiratobing.githubuser.util.theme.ModalBottomSheet
import submission.andhiratobing.githubuser.util.theme.Themes.Companion.THEMES_ARRAY
import submission.andhiratobing.githubuser.viewmodel.SettingsViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding as FragmentSettingsBinding
    private val settingsViewModel: SettingsViewModel by viewModels()
    @Inject lateinit var modalBottomSheet: ModalBottomSheet

    @Inject
    lateinit var reminderReceiver: ReminderReceiver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerThemes()
        setRepeatingReminder()
        observerReminderRepeating()

        binding.ibNextConfigurationTheme.setOnClickListener(this)
        binding.ibNextConfigurationLanguage.setOnClickListener(this)
        binding.layoutReminder.setOnClickListener(this)
    }

    private fun observerThemes() {
        settingsViewModel.getThemes.observe(
            viewLifecycleOwner, { currentTheme ->
                val appTheme = THEMES_ARRAY.firstOrNull { it.themeSelected == currentTheme }
                appTheme?.let {
                    binding.themeDescription.text = getString(it.themeNameRes)
                }
            }
        )
    }

    private fun setThemesApplication() {
        lifecycleScope.launch {
            val currentTheme = settingsViewModel.getThemes.value
            var checkedItem = THEMES_ARRAY.indexOfFirst { it.themeSelected == currentTheme }
            if (checkedItem >= 0) {
                val items = THEMES_ARRAY.map {
                    getText(it.themeNameRes)
                }.toTypedArray()
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(getString(R.string.choose_theme))
                    .setSingleChoiceItems(items, checkedItem) { _, it ->
                        checkedItem = it
                    }
                    .setPositiveButton(getString(R.string.save)) { _, _ ->
                        val mode = THEMES_ARRAY[checkedItem].themeSelected
                        AppCompatDelegate.setDefaultNightMode(mode)
                        settingsViewModel.setAppTheme(mode)
                        // update theme description
                        binding.themeDescription.text =
                            getString(THEMES_ARRAY[checkedItem].themeNameRes)
                    }
                    .setNegativeButton(getString(R.string.cancel)) { dialogInterface, _ ->
                        dialogInterface.dismiss()
                    }.show()
            }
        }
    }

    private fun observerReminderRepeating() {
        settingsViewModel.getReminder.observe(viewLifecycleOwner, { state ->
            binding.switchReminder.isChecked = state
            Log.d("Test observer", "$state")
        })
    }

    private fun setRepeatingReminder() {
        lifecycleScope.launch {
            binding.apply {
                switchReminder.setOnCheckedChangeListener { _, isChecked ->
                    when (isChecked) {
                        true -> {
                            settingsViewModel.setReminder(isChecked)
                            reminderReceiver.setRepeatingReminder(
                                requireActivity(),
                                ReminderReceiver.Companion.TypeReminder.TYPE_REPEATING
                            )
                            Log.d("Test", "$isChecked")
                        }
                        false -> {
                            settingsViewModel.setReminder(isChecked)
                            reminderReceiver.cancelReminders(
                                requireActivity(),
                                ReminderReceiver.Companion.TypeReminder.TYPE_REPEATING
                            )
                            Log.d("Test", "$isChecked")
                        }
                    }
                }
            }
        }
    }

    private fun gotoSettingLanguage() {
        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        requireActivity().startActivity(intent)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.ibNextConfigurationTheme -> setThemesApplication()
                R.id.ibNextConfigurationLanguage -> gotoSettingLanguage()
                R.id.layoutReminder -> descriptionReminders()
            }
        }
    }

    private fun descriptionReminders() {
        modalBottomSheet.show(parentFragmentManager, ModalBottomSheet.TAG)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}