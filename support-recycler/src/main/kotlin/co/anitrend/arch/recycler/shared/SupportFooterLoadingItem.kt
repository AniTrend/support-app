package co.anitrend.arch.recycler.shared

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.anitrend.arch.core.model.IStateLayoutConfig
import co.anitrend.arch.extension.gone
import co.anitrend.arch.recycler.R
import co.anitrend.arch.recycler.common.ClickableItem
import co.anitrend.arch.recycler.holder.SupportViewHolder
import co.anitrend.arch.recycler.model.RecyclerItem
import kotlinx.android.synthetic.main.support_layout_state_footer_loading.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Footer view holder for representing loading status
 *
 * @since v0.9.X
 */
open class SupportFooterLoadingItem(
    private val configuration: IStateLayoutConfig
) : RecyclerItem(RecyclerView.NO_ID) {

    @ExperimentalCoroutinesApi
    override fun bind(
        view: View,
        position: Int,
        payloads: List<Any>,
        stateFlow: MutableStateFlow<ClickableItem?>
    ) {
        if (configuration.loadingMessage != null)
            view.stateFooterLoadingText.setText(configuration.loadingMessage!!)
        else
            view.stateFooterLoadingText.gone()
    }

    override fun unbind(view: View) {
        view.stateFooterLoadingText.text = null
    }

    override fun getSpanSize(
        spanCount: Int,
        position: Int,
        resources: Resources
    ) = resources.getInteger(R.integer.single_list_size)

    companion object {
        /**
         * Inflates a layout and returns it's root view wrapped in [SupportViewHolder]
         *
         * @param viewGroup parent view requesting the layout
         * @param layoutInflater inflater to use, this is derived from the [viewGroup]
         */
        internal fun createViewHolder(
            viewGroup: ViewGroup,
            layoutInflater: LayoutInflater
        ): SupportViewHolder {
            val view = layoutInflater.inflate(
                R.layout.support_layout_state_footer_loading,
                viewGroup,
                false
            )
            return SupportViewHolder(view)
        }
    }
}