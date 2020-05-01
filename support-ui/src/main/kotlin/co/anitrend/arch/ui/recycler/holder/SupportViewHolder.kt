package co.anitrend.arch.ui.recycler.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.anitrend.arch.ui.action.contract.ISupportActionMode
import co.anitrend.arch.ui.action.decorator.ISelectionDecorator
import co.anitrend.arch.ui.recycler.holder.event.ItemClickListener

/**
 * Core implementation for [androidx.recyclerview.widget.RecyclerView.ViewHolder] with additional
 * functionality for supporting [ISupportActionMode]
 *
 * @since v1.1.0
 * @see ISupportActionMode
 */
abstract class SupportViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {

    var supportActionMode: ISupportActionMode<T>? = null

    var supportDecorator: ISelectionDecorator? =
        object : ISelectionDecorator {}

    /**
     * Constructs an int pair container with a boolean representing a valid adapter position
     *
     * @return [Pair] of [Int] and [Boolean]
     */
    private fun isValidIndexPair(): Pair<Int, Boolean> =
            Pair(adapterPosition, adapterPosition != RecyclerView.NO_POSITION)

    /**
     * Load images, text, buttons, etc. in this method from the given parameter
     *
     * @param model Is the liveData at the current adapter position
     */
    abstract operator fun invoke(model: T?)

    /**
     * Clear or unbind any references the views might be using, e.g. image loading
     * libraries, data binding, callbacks e.t.c
     */
    open fun onViewRecycled() {
        supportActionMode = null
        supportDecorator = null
    }

    /**
     * Handle any onclick events from our views, optionally you can call
     * [performClick] to dispatch [Pair]<[Int], T> on the [ItemClickListener]
     *
     * @param view the view that has been clicked
     * @param itemClickListener callback for handing clicks
     */
    abstract fun onItemClick(view: View, itemClickListener: ItemClickListener<T>)

    /**
     * Called when a view has been clicked and held. Optionally you can call
     * [performLongClick] to dispatch [Pair]<[Int], T> on the [ItemClickListener].
     *
     * If [ISupportActionMode] is then long clicking an items will start the section action mode
     *
     * @param view The view that was clicked and held.
     * @param itemClickListener callback for handing clicks
     *
     * @return [Boolean] true if the callback consumed the long click, false otherwise.
     */
    open fun onLongItemClick(view: View, itemClickListener: ItemClickListener<T>): Boolean = false

    /**
     * Applying selection styling on the desired item
     * @param model the current liveData item
     */
    @Deprecated("Use")
    fun onBindSelectionState(model: T?) =
        supportDecorator?.setBackgroundColor(
            itemView, supportActionMode?.containsItem(model) ?: false
        )

    /**
     * Handle any onclick events from our views
     *
     * @param view the view that has been clicked
     * @see View.OnClickListener
     */
    protected fun performClick(entity: T?, view: View, clickListener: ItemClickListener<T>) {
        val pair = isValidIndexPair()
        if (pair.second && isClickable(entity))
            clickListener.onItemClick(
                view, Pair(pair.first, entity)
            )
    }

    /**
     * Called when a view has been clicked and held.
     *
     * @param view The view that was clicked and held.
     * @return true if the supportActionMode consumed the long click, false otherwise.
     */
    protected fun performLongClick(entity: T?, view: View, clickListener: ItemClickListener<T>): Boolean {
        val pair = isValidIndexPair()
        return when (pair.second && isLongClickable(entity)) {
            true -> {
                clickListener.onItemLongClick(
                    view, Pair(pair.first, entity)
                )
                true
            }
            else -> false
        }
    }

    private fun isClickable(clicked: T?) =
        supportActionMode?.isSelectionClickable(
            itemView, supportDecorator, clicked
        ) ?: true



    private fun isLongClickable(clicked: T?) =
        supportActionMode?.isLongSelectionClickable(
            itemView, supportDecorator, clicked
        ) ?: true
}
