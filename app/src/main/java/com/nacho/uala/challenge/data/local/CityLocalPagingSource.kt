package com.nacho.uala.challenge.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nacho.uala.challenge.data.local.model.CityEntity

class CityLocalPagingSource(
    private val local: CityLocalDataSource,
    private val pageSize: Int
) : PagingSource<Int, CityEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CityEntity> {
        val page = params.key ?: 0
        val offset = page * pageSize

        return try {
            val data = local.getCities(limit = pageSize, offset = offset)

            LoadResult.Page(
                data = data,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (data.size < pageSize) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CityEntity>): Int? {
        return state.anchorPosition?.let { position ->
            position / pageSize
        }
    }
}