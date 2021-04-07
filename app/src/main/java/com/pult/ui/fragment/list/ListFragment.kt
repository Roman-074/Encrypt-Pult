package com.pult.ui.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pult.R
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var mListModel: ListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mListModel = ViewModelProvider(this).get(ListViewModel::class.java)

        var list = mutableListOf<String>()
        list_recycler_view.adapter = ListAdapter(requireContext(), list)
        list_recycler_view.layoutManager = LinearLayoutManager(requireContext())
    }

}