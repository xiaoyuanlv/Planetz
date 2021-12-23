package com.senlasy.planetz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.senlasy.planetz.R
import com.senlasy.planetz.model.Planet
import com.senlasy.planetz.mutility.PlanetImgHelper

class PlanetIconAdapter (
    var ItemList: MutableList<Planet>,
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
        fun onItemClick(item: Planet, view : View)
    }

    fun changeLayout(rowLayout : Int){
        this.rowLayout = rowLayout
    }

    fun addAll(item: List<Planet>) {
        ItemList.addAll(item)
        notifyDataSetChanged()
    }

    fun setData(lst: MutableList<Planet>){
        ItemList = lst
        notifyDataSetChanged()
    }

    fun remove(item: Planet) {
        val position = ItemList.indexOf(item)
        ItemList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        var vholder : RecyclerView.ViewHolder? = null
        if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
            vholder = PlanetViewHolder(view)
        }
        return vholder as RecyclerView.ViewHolder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PlanetViewHolder) {

            holder.bind(ItemList[position], listener, context)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return  VIEW_TYPE_ITEM
    }


    fun setOnItemListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    class PlanetViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var rlPlanet: RelativeLayout
        internal var txtPlanet: TextView
        internal var imgPlanet : ImageView

        init {
            txtPlanet = v.findViewById(R.id.txtPlanet)
            rlPlanet = v.findViewById(R.id.rlPlanet)
            imgPlanet = v.findViewById(R.id.imgPlanet)
        }

        fun bind(item: Planet, listener: OnItemClickListener?, context: Context) {
            setData(item)

            rlPlanet.setOnClickListener {
                rlPlanet.setBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.id), null))
                focusedItem = layoutPosition
                listener!!.onItemClick(item, it)
            }

        }

        fun setData(item: Planet){
            txtPlanet.text = item.title
            imgPlanet.setImageResource(PlanetImgHelper.getImageByPlanetID(item.id))
        }

    }

    companion object {
        private var focusedItem = -1
    }

}
