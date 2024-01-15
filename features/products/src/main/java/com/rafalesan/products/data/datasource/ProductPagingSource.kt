package com.rafalesan.products.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rafalesan.credikiosko.core.commons.data.utils.ApiResult
import com.rafalesan.products.data.dto.ProductDto

class ProductPagingSource(
    private val productRemoteDataSource: ProductRemoteDataSource
) : PagingSource<Int, ProductDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductDto> {
        params.loadSize
        val currentPage = params.key ?: 1
        val apiResult = productRemoteDataSource.requestProducts(
            currentPage
        )

        return when (apiResult) {
            is ApiResult.Error -> LoadResult.Error(apiResult.exception)
            is ApiResult.Success -> {
                val response = apiResult.response
                LoadResult.Page(
                    data = response.data,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (response.data.isEmpty()) null else response.currentPage + 1
                )
            }
        }

    }

    override fun getRefreshKey(state: PagingState<Int, ProductDto>): Int? {
        return state.anchorPosition
    }

}
