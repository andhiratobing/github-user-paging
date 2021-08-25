package submission.andhiratobing.githubuser.data.remote.responses.following

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FollowingResponse(
    @SerializedName("login")
    val username: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("avatar_url")
    val avatar: String
): Parcelable