package com.pult.ui.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.pult.R
import com.pult.databinding.FragmentVerticalListBinding

/**
 * This fragment shows a vertical list of ice creams.
 */
class VerticalListFragment : BaseListFragment() {

    override val optionsMenuId = 2

    override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        return FragmentVerticalListBinding.inflate(inflater, container, false)
    }

    override fun setupListLayoutManager(list: DragDropSwipeRecyclerView) {
        // Set vertical linear layout manager
        list.layoutManager = LinearLayoutManager(activity)
    }

    override fun setupListOrientation(list: DragDropSwipeRecyclerView) {
        // It is necessary to set the orientation in code so the list can work correctly
            DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_VERTICAL_DRAGGING
//            DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_UNCONSTRAINED_DRAGGING
    }

    override fun setupListItemLayout(list: DragDropSwipeRecyclerView) {
            setStandardItemLayoutAndDivider(list)
//            setCardViewItemLayoutAndNoDivider(list)
    }

    private fun setStandardItemLayoutAndDivider(list: DragDropSwipeRecyclerView) {
        // In XML: app:item_layout="@layout/list_item_vertical_list"
        list.itemLayoutId = R.layout.list_item_vertical_list

        // In XML: app:divider="@drawable/divider_vertical_list"
        list.dividerDrawableId = R.drawable.divider_vertical_list
    }

//    private fun setCardViewItemLayoutAndNoDivider(list: DragDropSwipeRecyclerView) {
//        // In XML: app:item_layout="@layout/list_item_vertical_list_cardview"
//        list.itemLayoutId = R.layout.list_item_vertical_list_cardview
//
//        // In XML: app:divider="@null"
//        list.dividerDrawableId = null
//    }

    override fun setupLayoutBehindItemLayoutOnSwiping(list: DragDropSwipeRecyclerView) {
        // We set to null all the properties that can be used to display something behind swiped items
        // In XML: app:behind_swiped_item_bg_color="@null"
        list.behindSwipedItemBackgroundColor = null

        // In XML: app:behind_swiped_item_bg_color_secondary="@null"
        list.behindSwipedItemBackgroundSecondaryColor = null

        // In XML: app:behind_swiped_item_icon="@null"
        list.behindSwipedItemIconDrawableId = null

        // In XML: app:behind_swiped_item_icon_secondary="@null"
        list.behindSwipedItemIconSecondaryDrawableId = null

        // In XML: app:behind_swiped_item_custom_layout="@null"
        list.behindSwipedItemLayoutId = null

        // In XML: app:behind_swiped_item_custom_layout_secondary="@null"
        list.behindSwipedItemSecondaryLayoutId = null

        val currentContext = context
                // We set certain properties to show an icon and a background colour behind swiped items
                // In XML: app:behind_swiped_item_icon="@drawable/ic_remove_item"
                list.behindSwipedItemIconDrawableId = R.drawable.ic_remove_item

                // In XML: app:behind_swiped_item_icon_secondary="@drawable/ic_archive_item"
                list.behindSwipedItemIconSecondaryDrawableId = R.drawable.ic_archive_item

                // In XML: app:behind_swiped_item_bg_color="@color/swipeBehindBackground"
                list.behindSwipedItemBackgroundColor = ContextCompat.getColor(requireContext(), R.color.swipeBehindBackground)

                // In XML: app:behind_swiped_item_bg_color_secondary="@color/swipeBehindBackgroundSecondary"
                list.behindSwipedItemBackgroundSecondaryColor = ContextCompat.getColor(requireContext(), R.color.swipeBehindBackgroundSecondary)

                // In XML: app:behind_swiped_item_icon_margin="@dimen/spacing_normal"
                list.behindSwipedItemIconMargin = resources.getDimension(R.dimen.spacing_normal)
                // We set our custom layouts to be displayed behind swiped items
                // In XML: app:behind_swiped_item_custom_layout="@layout/behind_swiped_vertical_list"
//                list.behindSwipedItemLayoutId = R.layout.behind_swiped_vertical_list

                // In XML: app:behind_swiped_item_custom_layout_secondary="@layout/behind_swiped_vertical_list_secondary"
//                list.behindSwipedItemSecondaryLayoutId = R.layout.behind_swiped_vertical_list_secondary
    }

    override fun setupFadeItemLayoutOnSwiping(list: DragDropSwipeRecyclerView) {
        // In XML: app:swiped_item_opacity_fades_on_swiping="true/false"
//        list.reduceItemAlphaOnSwiping = currentListFragmentConfig.isUsingFadeOnSwipedItems
    }

    companion object {
        fun newInstance() = VerticalListFragment()
    }

//    fun newInstance(): VerticalListFragment {
//        val args = Bundle()
//        val fragment = VerticalListFragment()
//        fragment.arguments = args
//        return fragment
//    }

}