package submission.andhiratobing.githubuser.data.remote.responses.searchusers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserResponse(
    val items: List<UserResponseItem>
) : Parcelable


