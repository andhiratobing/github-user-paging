package submission.andhiratobing.githubuser.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite_users")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "name") val name: String,
    val avatar: String,
    val company: String,
    val location: String,
    val repository: Int,
    val follower: Int,
    val following: Int
) : Parcelable