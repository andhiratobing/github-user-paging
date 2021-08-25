package submission.andhiratobing.githubuser.data.local.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "favorite_users")
data class FavoriteEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "username")
    val username: String,
    val name: String,
    @ColumnInfo(name = "avatar_url")
    val avatar: String,
    val company: String?,
    val bio: String?,
    val location: String?,
    val following: Int,
    val followers: Int,
    val repository: Int
) : Parcelable
