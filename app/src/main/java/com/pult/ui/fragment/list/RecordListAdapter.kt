package com.pult.ui.fragment.list

import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import com.google.android.material.textfield.TextInputEditText
import com.pult.R
import com.pult.app.utils.PrefUtils

/**
 * Adapter for a list of ice creams.
 */
class RecordListAdapter(dataSet: List<Record> = emptyList())
    : DragDropSwipeAdapter<Record, RecordListAdapter.ViewHolder>(dataSet) {

    class ViewHolder(iceCreamLayout: View) : DragDropSwipeAdapter.ViewHolder(iceCreamLayout) {
        val title: TextInputEditText = itemView.findViewById(R.id.list_item_title)
        val body: TextInputEditText = itemView.findViewById(R.id.list_item_body)
        val dragIcon: ImageView = itemView.findViewById(R.id.drag_icon)
        val icon: ImageView? = itemView.findViewById(R.id.list_item_icon)
    }

    override fun getViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(item: Record, viewHolder: ViewHolder, position: Int) {
        val context = viewHolder.itemView.context
//        viewHolder.title.addTextChangedListener(titleWatcher)
        viewHolder.body.addTextChangedListener(bodyWatcher)

//        Log.d("my", "onBindViewHolder >>>  $position")
    }

    private val bodyWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
        override fun afterTextChanged(s: Editable?) { }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            Log.d("my", "onTextChanged: $s")
        }
    }

    override fun onIsDragging(item: Record?, viewHolder: ViewHolder, offsetX: Int, offsetY: Int,
                              canvasUnder: Canvas?, canvasOver: Canvas?, isUserControlled: Boolean) { }
    override fun onIsSwiping(item: Record?, viewHolder: ViewHolder, offsetX: Int, offsetY: Int,
                             canvasUnder: Canvas?, canvasOver: Canvas?, isUserControlled: Boolean) { }

    override fun getViewToTouchToStartDraggingItem(item: Record, viewHolder: ViewHolder, position: Int) = viewHolder.dragIcon
    override fun onDragFinished(item: Record, viewHolder: ViewHolder) {}
    override fun onSwipeAnimationFinished(viewHolder: ViewHolder) {}
    override fun onDragStarted(item: Record, viewHolder: ViewHolder) {}
    override fun onSwipeStarted(item: Record, viewHolder: ViewHolder) {}

}