package submission.andhiratobing.githubuser.util

import submission.andhiratobing.githubuser.BuildConfig


class Constants {

    companion object {
        //api service github and database
        const val BASE_URL = "https://api.github.com/"
        const val ACCEPT_VERSION = "application/vnd.github.v3+json"
        const val DATABASE_NAME = "github_users"
        const val TABLE_NAME_FAVORITE = "favorite_users"
        const val API_GITHUB_KEY = BuildConfig.API_GITHUB_KEY

        //type share
        const val TYPE_SHARE = "text/plain"

        const val BACK_PRESS_TIME = 2_000L
        const val DELAY_MILLISECOND = 3_500L

        //request network set load page
        const val START_PAGE_INDEX = 1
        const val NETWORK_PAGE_SIZE = 10
        const val NETWORK_PRE_FETCH_DISTACE = 5
        const val NETWORK_INITIAL_LOAD_SIZE = 5

        //datastore preferences
        const val DATA_STORE_NAME = "settingsPref"
        const val THEMES_KEY = "themes_key"
        const val REMINDER_REPEATING_KEY = "reminder_repeating"
    }
}