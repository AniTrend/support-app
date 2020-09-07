package co.anitrend.arch.data.common

import androidx.paging.PagingRequestHelper

/**
 * Contract response handler for wrapping future requests
 *
 * This focuses on handling paging requests using [androidx.paging.PagingRequestHelper]
 *
 * @since v1.2.0
 */
interface ISupportPagingResponse<in RESOURCE> {

    /**
     * Response handler for coroutine contexts, mainly for paging
     *
     * @param resource awaiting execution
     * @param pagingRequestHelper optional paging request callback
     */
    suspend operator fun invoke(
        resource: RESOURCE,
        pagingRequestHelper: PagingRequestHelper.Request.Callback
    )
}