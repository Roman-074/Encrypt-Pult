package com.pult.ui.fragment.list

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemDragListener
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemSwipeListener
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnListScrollListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.pult.R
import com.pult.databinding.FragmentVerticalListBinding

/**
 * This fragment shows a vertical list of ice creams.
 */
class VerticalListFragment : Fragment() {

    private val adapter = IceCreamListAdapter()
    private val repository = IceCreamRepository.getInstance()

    private var _binding: ViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var list: DragDropSwipeRecyclerView
    private lateinit var loadingIndicator: ProgressBar
    private lateinit var button: FloatingActionButton

    private fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        return FragmentVerticalListBinding.inflate(inflater, container, false)
    }

    private fun setupListLayoutManager(list: DragDropSwipeRecyclerView) {
        list.layoutManager = LinearLayoutManager(activity)
    }

    private fun setupListOrientation(list: DragDropSwipeRecyclerView) {
            DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_VERTICAL_DRAGGING
    }

    private fun setupListItemLayout(list: DragDropSwipeRecyclerView) {
            setStandardItemLayoutAndDivider(list)
    }

    private fun setStandardItemLayoutAndDivider(list: DragDropSwipeRecyclerView) {
        list.itemLayoutId = R.layout.list_item_vertical_list
        list.dividerDrawableId = R.drawable.divider_vertical_list
    }

    private fun setupLayoutBehindItemLayoutOnSwiping(list: DragDropSwipeRecyclerView) {
        list.behindSwipedItemBackgroundColor = null
        list.behindSwipedItemBackgroundSecondaryColor = null
        list.behindSwipedItemIconDrawableId = null
        list.behindSwipedItemIconSecondaryDrawableId = null
        list.behindSwipedItemLayoutId = null
        list.behindSwipedItemSecondaryLayoutId = null
        list.behindSwipedItemIconDrawableId = R.drawable.ic_remove_item
        list.behindSwipedItemIconSecondaryDrawableId = R.drawable.ic_archive_item
        list.behindSwipedItemBackgroundColor = ContextCompat.getColor(requireContext(), R.color.swipeBehindBackground)
        list.behindSwipedItemBackgroundSecondaryColor = ContextCompat.getColor(requireContext(), R.color.swipeBehindBackgroundSecondary)
        list.behindSwipedItemIconMargin = resources.getDimension(R.dimen.spacing_normal)
    }

    private fun setupFadeItemLayoutOnSwiping(list: DragDropSwipeRecyclerView) {
        // In XML: app:swiped_item_opacity_fades_on_swiping="true/false"
//        list.reduceItemAlphaOnSwiping = currentListFragmentConfig.isUsingFadeOnSwipedItems
    }

    private fun reloadItems() {
        loadingIndicator.visibility = View.VISIBLE
        list.visibility = View.GONE
        loadData()
        Handler().postDelayed({
            loadingIndicator.visibility = View.GONE
            list.visibility = View.VISIBLE
        }, 150)
    }

    fun newInstance(): VerticalListFragment {
        val args = Bundle()
        val fragment = VerticalListFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        _binding = inflateViewBinding(inflater,container)
        list = binding.root.findViewById(R.id.list)
        loadingIndicator = binding.root.findViewById(R.id.loading_indicator)
        button = binding.root.findViewById(R.id.list_button_add)
        list.adapter = adapter
        list.swipeListener = onItemSwipeListener
        list.dragListener = onItemDragListener
        list.scrollListener = onListScrollListener

        setupListLayoutManager(list)
        setupListOrientation(list)
        setupListItemLayout(list)
        setupLayoutBehindItemLayoutOnSwiping(list)
        setupFadeItemLayoutOnSwiping(list)

        button.setOnClickListener {
            IceCreamRepository.getInstance().generateNewItem()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()

        repository.removeOnItemAdditionListener(onItemAddedListener)
    }

    override fun onResume() {
        super.onResume()

        repository.addOnItemAdditionListener(onItemAddedListener)
        reloadItems()
    }


    /////////////////////////////////__LISTENERS__/////////////////////////////////////////////////
    private val onItemSwipeListener = object : OnItemSwipeListener<IceCream> {
        override fun onItemSwiped(position: Int, direction: OnItemSwipeListener.SwipeDirection, item: IceCream): Boolean {
            when (direction) {
                OnItemSwipeListener.SwipeDirection.RIGHT_TO_LEFT -> onItemSwipedLeft(item, position)
                OnItemSwipeListener.SwipeDirection.LEFT_TO_RIGHT -> onItemSwipedRight(item, position)
                OnItemSwipeListener.SwipeDirection.DOWN_TO_UP -> onItemSwipedUp(item, position)
                OnItemSwipeListener.SwipeDirection.UP_TO_DOWN -> onItemSwipedDown(item, position)
            }

            return false
        }
    }

    private val onItemDragListener = object : OnItemDragListener<IceCream> {
        override fun onItemDragged(previousPosition: Int, newPosition: Int, item: IceCream) {
        }

        override fun onItemDropped(initialPosition: Int, finalPosition: Int, item: IceCream) {
            if (initialPosition != finalPosition) {

                // Change item position inside the repository
                repository.removeItem(item)
                repository.insertItem(item, finalPosition)
            } else {
            }
        }
    }

    private val onListScrollListener = object : OnListScrollListener {
        override fun onListScrollStateChanged(scrollState: OnListScrollListener.ScrollState) {
            // Call commented out to avoid saturating the log
            //Logger.log("List scroll state changed to $scrollState")
        }

        override fun onListScrolled(scrollDirection: OnListScrollListener.ScrollDirection, distance: Int) {
            // Call commented out to avoid saturating the log
            //Logger.log("List scrolled $distance pixels $scrollDirection")
        }
    }

    private val onItemAddedListener = object : BaseRepository.OnItemAdditionListener<IceCream> {
        override fun onItemAdded(item: IceCream, position: Int) {
            if (!adapter.dataSet.contains(item)) {
                adapter.insertItem(position, item)
                list.smoothScrollToPosition(position)
            }
        }
    }


    /////////////////////////////////__FUNCTIONS__/////////////////////////////////////////////////
    private fun loadData() { adapter.dataSet = repository.getAllItems() }
    private fun onItemSwipedLeft(item: IceCream, position: Int) { removeItem(item, position) }
    private fun onItemSwipedRight(item: IceCream, position: Int) { archiveItem(item, position) }
    private fun onItemSwipedUp(item: IceCream, position: Int) { archiveItem(item, position) }
    private fun onItemSwipedDown(item: IceCream, position: Int) { removeItem(item, position) }
    private fun removeItem(item: IceCream, position: Int) { removeItemFromList(item, position, R.string.itemRemovedMessage) }
    private fun archiveItem(item: IceCream, position: Int) { removeItemFromList(item, position, R.string.itemArchivedMessage) }
    private fun removeItemFromList(item: IceCream, position: Int, stringResourceId: Int) {
        repository.removeItem(item)
        val itemSwipedSnackBar = Snackbar.make(binding.root, getString(stringResourceId, item), Snackbar.LENGTH_SHORT)
        itemSwipedSnackBar.setAction(getString(R.string.undoCaps)) {
            repository.insertItem(item, position)
        }
        itemSwipedSnackBar.show()
    }

}