package io.wax911.support.data.repository.contract

import io.wax911.support.data.model.UiModel
import io.wax911.support.extension.util.SupportCoroutineHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


interface ISupportRepository<V, S> : SupportCoroutineHelper {

    /**
     * Handles dispatching of network requests to a background thread
     *
     * @param subject subject to apply business rules
     */
    operator fun invoke(subject: S): UiModel<V>

    /**
     * Deals with cancellation of any pending or on going operations that the repository is busy with
     */
    fun onCleared() {
        cancelAllChildren()
    }

    override val coroutineDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO
}