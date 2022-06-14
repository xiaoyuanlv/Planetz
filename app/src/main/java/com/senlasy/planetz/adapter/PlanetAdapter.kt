package com.senlasy.planetz.adapter

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.senlasy.planetz.R
import com.senlasy.planetz.model.Planet
import com.senlasy.planetz.mutility.PlanetImgHelper
import org.w3c.dom.Text

class PlanetAdapter(
    var ItemList: MutableList<Planet>,
    private var rowLayout: Int,
    private val context: Context,
    recyclerView: RecyclerView
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
        internal var txtDistance : TextView
        internal var txtDiameter : TextView


        init {
            txtPlanet = v.findViewById(R.id.txtPlanet)
            rlPlanet = v.findViewById(R.id.rlPlanet)
            imgPlanet = v.findViewById(R.id.imgPlanet)
            txtDistance = v.findViewById(R.id.txtDistance)
            txtDiameter = v.findViewById(R.id.txtDiameter)
        }

        fun bind(item: Planet, listener: OnItemClickListener?, context: Context) {
            setData(item, context)

            rlPlanet.setOnClickListener {
                rlPlanet.setBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.id), null))
                focusedItem = layoutPosition
                listener!!.onItemClick(item, imgPlanet)
            }

        }

        fun setData(item: Planet, context: Context){
            txtDiameter.text = item.diameter_km.toString() + " km "
            txtDistance.text = item.distance_thesun_km.toString() + " km "
            txtPlanet.text = item.title

            imgPlanet.setImageResource(PlanetImgHelper.getImageByPlanetID(item.id))
        }

    }

    companion object {
        private var focusedItem = -1
    }

}
