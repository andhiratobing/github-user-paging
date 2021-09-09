package com.example.simplegithubconsumer.data

import android.database.Cursor

object MappingHelper {

    fun cursorToList(cursor: Cursor?): ArrayList<FavoriteUser> {
        val listFavoriteUser = ArrayList<FavoriteUser>()
        cursor?.apply {
            while (moveToNext()) {
                listFavoriteUser.add(
                    FavoriteUser(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ID)),
                        username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME)),
                        name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.NAME)),
                        avatar = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR)),
                        company = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COMPANY)),
                        bio = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.BIO)),
                        location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOCATION)),
                        following = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWING)),
                        followers = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWERS)),
                        repository = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.REPOSITORY))
                    )
                )
            }
        }
        return listFavoriteUser
    }
}