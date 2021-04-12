package com.pult.ui.fragment.list

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
    private var binding: FragmentVerticalListBinding? = null
    private lateinit var viewModel: ListViewModel

    fun newInstance(): VerticalListFragment {
        val args = Bundle()
        val fragment = VerticalListFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentVerticalListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = binding?.list
        list?.adapter = adapter
        list?.swipeListener = onItemSwipeListener
        list?.dragListener = onItemDragListener
        list?.scrollListener = onListScrollListener
        viewModel.setupListLayoutManager(list!!, requireActivity())
        viewModel.setupListOrientation(list)
        viewModel.setupListItemLayout(list)
        viewModel.setupLayoutBehindItemLayoutOnSwiping(list, requireContext())
        viewModel.setupFadeItemLayoutOnSwiping(list)

        binding?.listButtonAdd?.setOnClickListener {
            IceCreamRepository.getInstance().generateNewItem()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
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

    private fun reloadItems() {
        binding?.loadingIndicator?.visibility = View.VISIBLE
        binding?.list?.visibility = View.GONE
        loadData()
        Handler().postDelayed({
            binding?.loadingIndicator?.visibility = View.GONE
            binding?.list?.visibility = View.VISIBLE
        }, 150)
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
                binding?.list?.smoothScrollToPosition(position)
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
        val itemSwipedSnackBar = Snackbar.make(binding?.root!!, getString(stringResourceId, item), Snackbar.LENGTH_SHORT)
        itemSwipedSnackBar.setAction(getString(R.string.undoCaps)) {
            repository.insertItem(item, position)
        }
        itemSwipedSnackBar.show()
    }

}