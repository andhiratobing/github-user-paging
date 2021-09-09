package submission.andhiratobing.githubuser.adapter.remote.paging.searchusers

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import submission.andhiratobing.githubuser.data.remote.api.ApiService
import submission.andhiratobing.githubuser.data.remote.responses.users.UserResponseItem
import submission.andhiratobing.githubuser.util.Constants.Companion.START_PAGE_INDEX
import java.io.IOException
import javax.inject.Inject

class UserPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val query: String,
) : PagingSource<Int, UserResponseItem>() {

    override fun getRefreshKey(state: PagingState<Int, UserResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserResponseItem> {
        val page = params.key ?: START_PAGE_INDEX
        val perPage = params.loadSize

        return try {
            val response = apiService.searchUsers(query, page, perPage)
            val result = response.items

            LoadResult.Page(
                data = result,
                prevKey = if (page == START_PAGE_INDEX) null else page - 1,
                nextKey = if (result.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}