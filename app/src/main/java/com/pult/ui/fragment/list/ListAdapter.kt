package com.pult.ui.fragment.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pult.R
import kotlinx.android.synthetic.main.list_item_card.view.*

class ListAdapter(
    private var context: Context,
    private var stringList: MutableList<String>
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>(){

    override fun getItemCount(): Int = 15

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.list_item_card, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.title.text = "Title $position"
        holder.description.text = "Description $position"

        var trigger = false
        holder.checkBox.setOnClickListener {
            trigger = if (!trigger) {
                holder.checkBox.setImageResource(R.drawable.ic_check)
                true
            } else {
                holder.checkBox.setImageResource(R.drawable.ic_check_disable)
                false
            }
        }
    }



    class ListViewHolder(var itemView: View) : RecyclerView.ViewHolder(itemView){
        var title = itemView.list_title!!
        var description = itemView.list_description!!
        var checkBox = itemView.list_checkbox!!
    }

}