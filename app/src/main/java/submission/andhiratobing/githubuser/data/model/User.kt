package submission.andhiratobing.githubuser.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val users: List<UserEntity>
) : Parcelable