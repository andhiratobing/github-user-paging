package submission.andhiratobing.githubuser.adapter.remote.paging.reposusers

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import submission.andhiratobing.githubuser.data.remote.api.ApiService
import submission.andhiratobing.githubuser.data.remote.responses.repos.ReposResponse
import submission.andhiratobing.githubuser.util.Constants.Companion.START_PAGE_INDEX
import java.io.IOException
import javax.inject.Inject

class ReposPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val username: String,
) : PagingSource<Int, ReposResponse>() {

    override fun getRefreshKey(state: PagingState<Int, ReposResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReposResponse> {
        val page = params.key ?: START_PAGE_INDEX
        val perPage = params.loadSize

        return try {
            val response = apiService.getRepository(username, page, perPage)
            LoadResult.Page(
                data = response,
                prevKey = if (page == START_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}