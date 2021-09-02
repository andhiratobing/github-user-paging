package submission.andhiratobing.githubuser.util

import submission.andhiratobing.githubuser.BuildConfig


class Constants {

    companion object {
        const val BACK_PRESS_TIME = 2_000L
        const val BASE_URL = "https://api.github.com/"
        const val ACCEPT_VERSION = "application/vnd.github.v3+json"
        const val API_GITHUB_KEY = BuildConfig.API_GITHUB_KEY
        const val DELAY_MILLISECOND = 3_500L
        const val START_PAGE_INDEX = 1
        const val NETWORK_PAGE_SIZE = 10
        const val NETWORK_PRE_FETCH_DISTACE = 5
        const val NETWORK_INITIAL_LOAD_SIZE = 5

    }
}