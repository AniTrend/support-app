package io.wax911.support.ui.recycler.adapter

import android.view.View
import android.widget.ProgressBar
import androidx.work.impl.constraints.NetworkState
import io.wax911.support.ui.R
import io.wax911.support.ui.recycler.holder.SupportViewHolder
import io.wax911.support.ui.util.SupportStateLayoutConfiguration
import io.wax911.support.ui.view.text.SingleLineTextView

/**
 * Footer view holder for representing loading status
 *
 * @since 0.9.X
 */
class SupportFooterLoadingViewHolder<T>(
    view: View,
    private val configuration: SupportStateLayoutConfiguration
) : SupportViewHolder<T>(view.rootView) {

    private val stateText : SingleLineTextView? = view.findViewById(R.id.stateFooterLoadingText)

    /**
     * Load images, text, buttons, etc. in this method from the given parameter
     *
     * @param model Is the liveData at the current adapter position
     */
    override fun invoke(model: T?) {
        stateText?.setText(configuration.loadingMessage)
    }

    /**
     * If any image views are used within the view holder, clear any pending async requests
     * by using [com.bumptech.glide.RequestManager.clear]
     *
     * @see com.bumptech.glide.Glide
     */
    override fun onViewRecycled() {

    }

    /**
     * Handle any onclick events from our views, optionally you can call
     * [performClick] to dispatch [Pair]<[Int], T> on the [ItemClickListener]
     *
     * @param view the view that has been clicked
     */
    override fun onItemClick(view: View) {

    }
}