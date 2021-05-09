package com.pult.ui.fragment.list

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemDragListener
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemSwipeListener
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnListScrollListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.pult.R
import com.pult.databinding.FragmentVerticalListBinding

class VerticalListFragment : Fragment() {

    private val adapter = RecordListAdapter()
    private val repository = RecordRepo.getInstance()
    private var binding: FragmentVerticalListBinding? = null
    private lateinit var viewModel: ListViewModel
    private val titleArray = arrayListOf<String>()
    private val bodyArray = arrayListOf<String>()

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

        binding?.listButtonAdd?.setOnClickListener {
            RecordRepo.getInstance().generateNewItem()
        }

        binding?.listButtonSave?.setOnClickListener {
            titleArray.clear()
            bodyArray.clear()
            list.forEach {
                val body = it.findViewById<TextInputEditText>(R.id.list_item_body).text.toString()
                val title = it.findViewById<TextInputEditText>(R.id.list_item_title).text.toString()
                titleArray.add(title)
                bodyArray.add(body)
            }

            PackageData().startPackage(bodyArray)
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

    /////////////////////////////////__LISTENERS__/////////////////////////////////////////////////
    private val onItemSwipeListener = object : OnItemSwipeListener<Record> {
        override fun onItemSwiped(position: Int, direction: OnItemSwipeListener.SwipeDirection, item: Record): Boolean {
            when (direction) {
                OnItemSwipeListener.SwipeDirection.RIGHT_TO_LEFT -> onItemSwipedLeft(item, position)
                OnItemSwipeListener.SwipeDirection.LEFT_TO_RIGHT -> onItemSwipedRight(item, position)
                OnItemSwipeListener.SwipeDirection.DOWN_TO_UP -> onItemSwipedUp(item, position)
                OnItemSwipeListener.SwipeDirection.UP_TO_DOWN -> onItemSwipedDown(item, position)
            }

            return false
        }
    }

    private val onItemDragListener = object : OnItemDragListener<Record> {
        override fun onItemDragged(previousPosition: Int, newPosition: Int, item: Record) {
        }

        override fun onItemDropped(initialPosition: Int, finalPosition: Int, item: Record) {
            if (initialPosition != finalPosition) {
                repository.removeItem(item)
                repository.insertItem(item, finalPosition)
            } else {
            }
        }
    }

    private val onListScrollListener = object : OnListScrollListener {
        override fun onListScrollStateChanged(scrollState: OnListScrollListener.ScrollState) {}
        override fun onListScrolled(scrollDirection: OnListScrollListener.ScrollDirection, distance: Int) {}
    }

    private val onItemAddedListener = object : BaseRepository.OnItemAdditionListener<Record> {
        override fun onItemAdded(item: Record, position: Int) {
            if (!adapter.dataSet.contains(item)) {
                adapter.insertItem(position, item)
                binding?.list?.smoothScrollToPosition(position)
            }
        }
    }

    /////////////////////////////////__FUNCTIONS__/////////////////////////////////////////////////
    private fun onItemSwipedLeft(item: Record, position: Int) { removeItem(item, position) }
    private fun onItemSwipedRight(item: Record, position: Int) { archiveItem(item, position) }
    private fun onItemSwipedUp(item: Record, position: Int) { archiveItem(item, position) }
    private fun onItemSwipedDown(item: Record, position: Int) { removeItem(item, position) }
    private fun removeItem(item: Record, position: Int) { removeItemFromList(item, position, R.string.itemRemovedMessage) }
    private fun archiveItem(item: Record, position: Int) { removeItemFromList(item, position, R.string.itemArchivedMessage) }

    private fun removeItemFromList(item: Record, position: Int, stringResourceId: Int) {
        repository.removeItem(item)
        val itemSwipedSnackBar = Snackbar.make(binding?.root!!, getString(stringResourceId, item), Snackbar.LENGTH_SHORT)
        itemSwipedSnackBar.setAction(getString(R.string.undoCaps)) {
            repository.insertItem(item, position)
        }
        itemSwipedSnackBar.show()
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

    private fun loadData() {
        adapter.dataSet = repository.getAllItems()
//        Log.d("my", "loadData size: ${adapter.dataSet.size} ")
    }

}