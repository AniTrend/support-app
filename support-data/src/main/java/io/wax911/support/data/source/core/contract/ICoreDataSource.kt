package io.wax911.support.data.source.core.contract

import io.wax911.support.data.source.contract.IDataSource

interface ICoreDataSource : IDataSource {

    /**
     * Dispatches work for the paging data source to respective workers or mappers
     * that publish the result to any [androidx.lifecycle.LiveData] observers
     *
     * @see networkState
     */
    operator fun invoke()

    /**
     * Clears data sources (databases, preferences, e.t.c)
     */
    fun clearDataSource()

    /**
     * Invokes [clearDataSource] and should invoke network refresh or reload
     */
    fun invalidateAndRefresh() {
        clearDataSource()
    }

    /**
     * Performs the necessary operation to invoke a network retry request
     */
    fun retryRequest()
}