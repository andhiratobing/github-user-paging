package submission.andhiratobing.githubuser.data.remote.responses.detailusers

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUserResponse(
    @SerializedName("login")
    val username: String,
    val name: String,
    @SerializedName("avatar_url")
    val avatar: String,
    val bio: String?,
    val location: String?,
    val company: String?,
    val following: Int,
    val followers: Int,
    @SerializedName("public_repos")
    val repository: Int?
) : Parcelable
