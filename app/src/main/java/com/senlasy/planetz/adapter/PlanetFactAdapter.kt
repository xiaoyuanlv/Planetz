package com.senlasy.planetz.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.senlasy.planetz.R
import com.senlasy.planetz.model.PlanetFacts
import com.senlasy.planetz.mutility.PlanetImgHelper

class PlanetFactAdapter (
    var ItemList: MutableList<PlanetFacts>,
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
        fun onItemClick(item: PlanetFacts, view : View)
        fun onFavItemClick(item : PlanetFacts, view : TextView)
    }

    fun changeLayout(rowLayout : Int){
        this.rowLayout = rowLayout
    }

    fun addAll(item: List<PlanetFacts>) {
        ItemList.addAll(item)
        notifyDataSetChanged()
    }

    fun setData(lst: MutableList<PlanetFacts>){
        ItemList = lst
        notifyDataSetChanged()
    }

    fun remove(item: PlanetFacts) {
        val position = ItemList.indexOf(item)
        ItemList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        var vholder : RecyclerView.ViewHolder? = null
        if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
            vholder = PlanetFactsViewHolder(view)
        }
        return vholder as RecyclerView.ViewHolder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PlanetFactsViewHolder) {

            holder.bind(ItemList[position], listener, context)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return  VIEW_TYPE_ITEM
    }


    fun setOnItemListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    class PlanetFactsViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var cardContent: CardView
        internal var txtDescription: TextView
        internal var imgPlanet : ImageView
        internal var ftvStar : TextView
        internal var cardFactBg : CardView


        init {

            cardContent = v.findViewById(R.id.cardContent)
            txtDescription = v.findViewById(R.id.txtDescription)
            imgPlanet = v.findViewById(R.id.imgPlanet)
            ftvStar = v.findViewById(R.id.ftvStar)
            cardFactBg = v.findViewById(R.id.cardFactBg)

        }

        fun bind(item: PlanetFacts, listener: OnItemClickListener?, context: Context) {
            setData(item, context)

            cardContent.setOnClickListener {
                // lnPlanetFacts.setBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.id), null))
                focusedItem = layoutPosition
                listener!!.onItemClick(item, it)
            }

            ftvStar.setOnClickListener {
                listener!!.onFavItemClick(item, ftvStar)
            }

        }

        fun setData(item: PlanetFacts, context: Context){
            imgPlanet.setImageResource(PlanetImgHelper.getImageByPlanetID(item.planet_id))
            txtDescription.text = item.description

            if(item.fav == 1){
                ftvStar.setTextColor(context.resources.getColor(R.color.colorAccent, null))
            } else {
                ftvStar.setTextColor(context.resources.getColor(android.R.color.white, null))
            }

            cardFactBg.setCardBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.planet_id), null))

        }

    }

    companion object {
        private var focusedItem = -1
    }

}
