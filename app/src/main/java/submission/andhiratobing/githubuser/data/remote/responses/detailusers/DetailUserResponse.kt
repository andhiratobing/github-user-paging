package submission.andhiratobing.githubuser.data.remote.responses.detailusers

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUserResponse(
    val id: Int,
    @SerializedName("login")
    val username: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("avatar_url")
    val avatar: String,
    val company: String,
    val bio: String,
    val location: String,
    val following: Int,
    @SerializedName("following_url")
    val following_url: String,
    val followers: Int,
    @SerializedName("followers_url")
    val followers_url: String,
    @SerializedName("public_repos")
    val repository: Int,
    @SerializedName("repos_url")
    val repos_url: String
) : Parcelable
