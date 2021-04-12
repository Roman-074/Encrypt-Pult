package com.pult.ui.fragment.list

import android.app.Activity
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.pult.R

class ListViewModel : ViewModel() {

    init {

    }

    public fun setupListLayoutManager(list: DragDropSwipeRecyclerView, activity: Activity) {
        list.layoutManager = LinearLayoutManager(activity)
    }

    public fun setupListOrientation(list: DragDropSwipeRecyclerView) {
        DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_VERTICAL_DRAGGING
    }

    public fun setupListItemLayout(list: DragDropSwipeRecyclerView) {
        setStandardItemLayoutAndDivider(list)
    }

    private fun setStandardItemLayoutAndDivider(list: DragDropSwipeRecyclerView) {
        list.itemLayoutId = R.layout.list_item_vertical_list
        list.dividerDrawableId = R.drawable.divider_vertical_list
    }

    public fun setupLayoutBehindItemLayoutOnSwiping(list: DragDropSwipeRecyclerView, context: Context) {
        list.behindSwipedItemBackgroundColor = null
        list.behindSwipedItemBackgroundSecondaryColor = null
        list.behindSwipedItemIconDrawableId = null
        list.behindSwipedItemIconSecondaryDrawableId = null
        list.behindSwipedItemLayoutId = null
        list.behindSwipedItemSecondaryLayoutId = null
        list.behindSwipedItemIconDrawableId = R.drawable.ic_remove_item
        list.behindSwipedItemIconSecondaryDrawableId = R.drawable.ic_archive_item
        list.behindSwipedItemBackgroundColor = ContextCompat.getColor(context, R.color.swipeBehindBackground)
        list.behindSwipedItemBackgroundSecondaryColor = ContextCompat.getColor(context, R.color.swipeBehindBackgroundSecondary)
        list.behindSwipedItemIconMargin = context.resources.getDimension(R.dimen.spacing_normal)
    }

    public fun setupFadeItemLayoutOnSwiping(list: DragDropSwipeRecyclerView) {
        // In XML: app:swiped_item_opacity_fades_on_swiping="true/false"
//        list.reduceItemAlphaOnSwiping = currentListFragmentConfig.isUsingFadeOnSwipedItems
    }

}