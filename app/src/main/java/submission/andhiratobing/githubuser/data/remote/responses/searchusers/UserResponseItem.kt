package submission.andhiratobing.githubuser.data.remote.responses.searchusers

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserResponseItem(
    @SerializedName("login")
    val username: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("avatar_url")
    val avatar: String
) : Parcelable