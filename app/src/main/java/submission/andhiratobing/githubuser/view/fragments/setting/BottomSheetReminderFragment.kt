package submission.andhiratobing.githubuser.view.fragments.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import submission.andhiratobing.githubuser.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BottomSheetReminderFragment @Inject constructor() : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.layout_bottom_sheet_dialog_reminder, container, false)


    companion object {
        const val TAG = "ModalBottomSheet"
    }

}