package com.pult.ui.fragment.list

import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import androidx.core.widget.ImageViewCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import com.pult.R
import java.util.logging.Logger

/**
 * Adapter for a list of ice creams.
 */
class IceCreamListAdapter(dataSet: List<IceCream> = emptyList())
    : DragDropSwipeAdapter<IceCream, IceCreamListAdapter.ViewHolder>(dataSet) {

    class ViewHolder(iceCreamLayout: View) : DragDropSwipeAdapter.ViewHolder(iceCreamLayout) {
        val iceCreamNameView: TextView = itemView.findViewById(R.id.ice_cream_name)
        val iceCreamPriceView: TextView = itemView.findViewById(R.id.ice_cream_price)
        val dragIcon: ImageView = itemView.findViewById(R.id.drag_icon)
        val iceCreamIcon: ImageView? = itemView.findViewById(R.id.ice_cream_icon)
//        val iceCreamPhotoFilter: View? = itemView.findViewById(R.id.ice_cream_photo_filter)
    }

    override fun getViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(item: IceCream, viewHolder: ViewHolder, position: Int) {
        val context = viewHolder.itemView.context
        viewHolder.iceCreamNameView.text = item.name
        if (viewHolder.iceCreamIcon != null) {
//            ImageViewCompat.setImageTintList(viewHolder.iceCreamIcon, ColorStateList.valueOf(iceCreamIconColor))
        }
    }

    override fun onIsDragging(
            item: IceCream?,
            viewHolder: ViewHolder,
            offsetX: Int,
            offsetY: Int,
            canvasUnder: Canvas?,
            canvasOver: Canvas?,
            isUserControlled: Boolean) {
    }

    override fun onIsSwiping(
            item: IceCream?,
            viewHolder: ViewHolder,
            offsetX: Int,
            offsetY: Int,
            canvasUnder: Canvas?,
            canvasOver: Canvas?,
            isUserControlled: Boolean) {
    }

    override fun getViewToTouchToStartDraggingItem(item: IceCream, viewHolder: ViewHolder, position: Int) = viewHolder.dragIcon
    override fun onDragFinished(item: IceCream, viewHolder: ViewHolder) {}
    override fun onSwipeAnimationFinished(viewHolder: ViewHolder) {}
    override fun onDragStarted(item: IceCream, viewHolder: ViewHolder) {}
    override fun onSwipeStarted(item: IceCream, viewHolder: ViewHolder) {}
}