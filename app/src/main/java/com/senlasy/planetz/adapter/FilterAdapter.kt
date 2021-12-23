package com.senlasy.Stringz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.senlasy.planetz.R

class FilterAdapter(
var ItemList: MutableList<String>,
private var rowLayout: Int,
private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listener: OnItemClickListener? = null

    // for load more
    private val VIEW_TYPE_ITEM = 0


    override fun getItemCount(): Int {
        if (ItemList.isEmpty()) return 0 else return ItemList.size
    }

    public interface OnItemClickListener {
        fun onItemClick(item: String, view : View, position: Int)
    }

    fun addAll(item: List<String>) {
        ItemList.addAll(item)
        notifyDataSetChanged()
    }

    fun setData(lst: MutableList<String>){
        ItemList = lst
        notifyDataSetChanged()
    }

    fun remove(item: String) {
        val position = ItemList.indexOf(item)
        ItemList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        var vholder : RecyclerView.ViewHolder? = null
        if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
            vholder = StringViewHolder(view)
        }
        return vholder as RecyclerView.ViewHolder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is StringViewHolder) {

            holder.bind(ItemList[position], position, listener, context)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return  VIEW_TYPE_ITEM
    }


    fun setOnItemListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    class StringViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var cardChoose: CardView
        internal var  rlFilter : RelativeLayout
        internal var txtTitle: TextView

        init {
            rlFilter = v.findViewById(R.id.rlFilter)
            cardChoose = v.findViewById(R.id.cardChoose)
            txtTitle = v.findViewById(R.id.txtTitle)
        }

        fun bind(item: String, position: Int, listener: OnItemClickListener?, context: Context) {
            setData(item, context)

            rlFilter.setOnClickListener {
                rlFilter.setBackgroundColor(context.resources.getColor(R.color.colorAccent, null))
                focusedItem = layoutPosition
                listener!!.onItemClick(item, it, position)
            }

        }

        fun setData(item: String, context: Context){
            txtTitle.text = item
        }

    }

    companion object {
        private var focusedItem = -1
    }

}
