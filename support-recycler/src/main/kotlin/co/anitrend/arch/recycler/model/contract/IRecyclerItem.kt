package co.anitrend.arch.recycler.model.contract

import android.view.View
import co.anitrend.arch.recycler.action.decorator.ISelectionDecorator
import co.anitrend.arch.recycler.common.ClickableItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Contract for recycler item
 *
 * @property id id of the item view
 * @property decorator provides styling information
 * @property supportsSelectionMode whether or not the element can trigger action mode
 *
 * @see IRecyclerItemSpan
 *
 * @since v1.3.0
 */
interface IRecyclerItem : IRecyclerItemSpan {
    val id: Long?

    val supportsSelectionMode: Boolean

    val decorator: ISelectionDecorator

    /**
     * Called when the [view] needs to be setup, this could be to set click listeners,
     * assign text, load images, e.t.c
     *
     * @param view view that was inflated
     * @param position current position
     * @param payloads optional payloads which maybe empty
     * @param stateFlow observable to broadcast click events
     */
    @ExperimentalCoroutinesApi
    fun bind(
        view: View,
        position: Int,
        payloads: List<Any>,
        stateFlow: MutableStateFlow<ClickableItem?>
    )

    /**
     * Called when the view needs to be recycled for reuse, clear any held references
     * to objects, stop any asynchronous work, e.t.c
     */
    fun unbind(view: View)
}