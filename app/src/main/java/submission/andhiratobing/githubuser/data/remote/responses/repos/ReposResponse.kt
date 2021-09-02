package submission.andhiratobing.githubuser.data.remote.responses.repos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReposResponse(
    val id: Int,
    val name: String,
    val description: String?,
    val language: String?,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("open_issues")
    val issuesCount: Int,
    @SerializedName("stargazers_count")
    val starCount: Int
) : Parcelable