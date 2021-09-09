package submission.andhiratobing.githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import submission.andhiratobing.githubuser.BuildConfig
import submission.andhiratobing.githubuser.data.local.dao.provider.FavoriteUserProviderDao
import submission.andhiratobing.githubuser.data.local.db.AppDatabase


class FavoriteUserProvider : ContentProvider() {

    private lateinit var favoriteUserProviderDao: FavoriteUserProviderDao

    init {
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE_CODE)
    }

    override fun onCreate(): Boolean {
        favoriteUserProviderDao =
            AppDatabase.getDatabase(context as Context).favoriteUserProvideDao()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor {
        val cursor: Cursor = when (uriMatcher.match(uri)) {
            FAVORITE_CODE -> favoriteUserProviderDao.getDataFavoriteForProvider()
            else -> throw IllegalArgumentException("Unknown Uri $uri")
        }

        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun getType(uri: Uri): String {
        return "vnd.android.cursor.dir/$AUTHORITY.$TABLE_NAME"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    companion object {
        const val AUTHORITY = BuildConfig.APPLICATION_ID
        const val TABLE_NAME = "favorite_users"
        private const val FAVORITE_CODE = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    }



}