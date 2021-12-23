package com.senlasy.planetz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.senlasy.planetz.R
import com.senlasy.planetz.model.SunFacts
import info.androidhive.fontawesome.FontTextView

class SunFactAdapter  (
    var ItemList: MutableList<SunFacts>,
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
        fun onItemClick(item: SunFacts, view : View)
        fun onFavItemClick(item : SunFacts, view : FontTextView)
    }

    fun changeLayout(rowLayout : Int){
        this.rowLayout = rowLayout
    }

    fun addAll(item: List<SunFacts>) {
        ItemList.addAll(item)
        notifyDataSetChanged()
    }

    fun setData(lst: MutableList<SunFacts>){
        ItemList = lst
        notifyDataSetChanged()
    }

    fun remove(item: SunFacts) {
        val position = ItemList.indexOf(item)
        ItemList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        var vholder : RecyclerView.ViewHolder? = null
        if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
            vholder = SunFactsViewHolder(view)
        }
        return vholder as RecyclerView.ViewHolder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SunFactsViewHolder) {

            holder.bind(ItemList[position], listener, context)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return  VIEW_TYPE_ITEM
    }


    fun setOnItemListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    class SunFactsViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var cardContent: CardView
        internal var txtDescription: TextView
        internal var imgPlanet : ImageView
        internal var ftvStar : FontTextView
        internal var cardFactBg : CardView


        init {

            cardContent = v.findViewById(R.id.cardContent)
            txtDescription = v.findViewById(R.id.txtDescription)
            imgPlanet = v.findViewById(R.id.imgPlanet)
            ftvStar = v.findViewById(R.id.ftvStar)
            cardFactBg = v.findViewById(R.id.cardFactBg)

        }

        fun bind(item: SunFacts, listener: OnItemClickListener?, context: Context) {
            setData(item, context)

            cardContent.setOnClickListener {
                // lnSunFacts.setBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.id), null))
                focusedItem = layoutPosition
                listener!!.onItemClick(item, it)
            }

            ftvStar.setOnClickListener {
                listener!!.onFavItemClick(item, ftvStar)
            }

        }

        fun setData(item: SunFacts, context: Context){
            imgPlanet.setImageResource(R.mipmap.sun)
            txtDescription.text = item.description

            if(item.fav == 1){
                ftvStar.setTextColor(context.resources.getColor(R.color.colorAccent, null))
            } else {
                ftvStar.setTextColor(context.resources.getColor(android.R.color.white, null))
            }
            ftvStar.visibility = View.GONE

            cardFactBg.setCardBackgroundColor(context.resources.getColor(R.color.sun_color, null))

        }

    }

    companion object {
        private var focusedItem = -1
    }

}
