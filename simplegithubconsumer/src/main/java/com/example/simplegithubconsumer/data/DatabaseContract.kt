package com.example.simplegithubconsumer.data

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "submission.andhiratobing.githubuser"
    const val SCHEME = "content"

    internal class FavoriteColumns : BaseColumns {
        companion object {
            private const val TABLE_NAME = "favorite_users"
            const val ID = "id"
            const val USERNAME = "username"
            const val NAME = "name"
            const val AVATAR = "avatar_url"
            const val COMPANY = "company"
            const val BIO = "bio"
            const val LOCATION = "location"
            const val FOLLOWING = "following"
            const val FOLLOWERS = "followers"
            const val REPOSITORY = "repository"


            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }

}