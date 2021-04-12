package com.pult.ui.fragment.list

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemDragListener
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemSwipeListener
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnListScrollListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.pult.R
import kotlinx.android.synthetic.main.fragment_vertical_list.*

/**
 * The base implementation of a fragment that displays a list of ice creams.
 */
abstract class BaseListFragment : Fragment() {


    private val adapter = IceCreamListAdapter()
    private val repository = IceCreamRepository.getInstance()

    private var _binding: ViewBinding? = null
    private val binding get() = _binding!!

    private lateinit var list: DragDropSwipeRecyclerView
    private lateinit var loadingIndicator: ProgressBar
    private lateinit var button: FloatingActionButton

    protected abstract val optionsMenuId: Int

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
            // Add the item to the adapter's data set if necessary
            if (!adapter.dataSet.contains(item)) {

                adapter.insertItem(position, item)

                // We scroll to the position of the added item (positions match in both adapter and repository)
                list.smoothScrollToPosition(position)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        // Set root view for the fragment and find the views
        _binding = inflateViewBinding(inflater,container)
        list = binding.root.findViewById(R.id.list)
        loadingIndicator = binding.root.findViewById(R.id.loading_indicator)
        button = binding.root.findViewById(R.id.list_button_add)

        // Set adapter and listeners
        list.adapter = adapter
        list.swipeListener = onItemSwipeListener
        list.dragListener = onItemDragListener
        list.scrollListener = onListScrollListener

        // Finish list setup
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

    protected abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding

    protected abstract fun setupListLayoutManager(list: DragDropSwipeRecyclerView)

    protected abstract fun setupListOrientation(list: DragDropSwipeRecyclerView)

    protected abstract fun setupListItemLayout(list: DragDropSwipeRecyclerView)

    protected abstract fun setupLayoutBehindItemLayoutOnSwiping(list: DragDropSwipeRecyclerView)

    protected abstract fun setupFadeItemLayoutOnSwiping(list: DragDropSwipeRecyclerView)


    override fun onPause() {
        super.onPause()

        // Unsubscribe from repository changes
        repository.removeOnItemAdditionListener(onItemAddedListener)
    }

    override fun onResume() {
        super.onResume()

        // Subscribe to repository changes and then reload items
        repository.addOnItemAdditionListener(onItemAddedListener)
        reloadItems()
    }

    private fun reloadItems() {
        // Show loader view
        loadingIndicator.visibility = View.VISIBLE
        list.visibility = View.GONE

        loadData()

        // Hide loader view after a small delay to simulate real data retrieval
        Handler().postDelayed({
            loadingIndicator.visibility = View.GONE
            list.visibility = View.VISIBLE
        }, 150)
    }

    private fun loadData() {
        adapter.dataSet = repository.getAllItems()
    }

    private fun onItemSwipedLeft(item: IceCream, position: Int) {

        removeItem(item, position)
    }

    private fun onItemSwipedRight(item: IceCream, position: Int) {

        archiveItem(item, position)
    }

    private fun onItemSwipedUp(item: IceCream, position: Int) {

        archiveItem(item, position)
    }

    private fun onItemSwipedDown(item: IceCream, position: Int) {

        removeItem(item, position)
    }

    private fun removeItem(item: IceCream, position: Int) {

        removeItemFromList(item, position, R.string.itemRemovedMessage)
    }

    private fun archiveItem(item: IceCream, position: Int) {

        removeItemFromList(item, position, R.string.itemArchivedMessage)
    }

    private fun removeItemFromList(item: IceCream, position: Int, stringResourceId: Int) {
        repository.removeItem(item)

        val itemSwipedSnackBar = Snackbar.make(binding.root, getString(stringResourceId, item), Snackbar.LENGTH_SHORT)
        itemSwipedSnackBar.setAction(getString(R.string.undoCaps)) {
            repository.insertItem(item, position)
        }
        itemSwipedSnackBar.show()
    }

}