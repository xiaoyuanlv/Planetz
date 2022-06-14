package com.senlasy.planetz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.senlasy.planetz.R
import com.senlasy.planetz.model.Moons
import com.senlasy.planetz.mutility.PlanetImgHelper

class MoonListAdapter  (
    var ItemList: MutableList<Moons>,
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
        fun onItemClick(item: Moons, view : View)
    }

    fun changeLayout(rowLayout : Int){
        this.rowLayout = rowLayout
    }

    fun addAll(item: List<Moons>) {
        ItemList.addAll(item)
        notifyDataSetChanged()
    }

    fun setData(lst: MutableList<Moons>){
        ItemList = lst
        notifyDataSetChanged()
    }

    fun remove(item: Moons) {
        val position = ItemList.indexOf(item)
        ItemList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        var vholder : RecyclerView.ViewHolder? = null
        if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
            vholder = MoonsViewHolder(view)
        }
        return vholder as RecyclerView.ViewHolder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MoonsViewHolder) {

            holder.bind(ItemList[position], listener, context)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return  VIEW_TYPE_ITEM
    }


    fun setOnItemListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    class MoonsViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var rlContent: RelativeLayout
        internal var txtTitle: TextView
        internal var txtYear : TextView

        internal var imgPlanetDistance : ImageView
        internal var ftvPlanetDistance  : TextView
        internal var txtPlanetDistance  : TextView
        internal var txtDiameter : TextView
        internal var txtOrbitalPeriod : TextView


        init {

            rlContent = v.findViewById(R.id.rlContent)
            txtTitle = v.findViewById(R.id.txtTitle)
            txtYear = v.findViewById(R.id.txtYear)
            imgPlanetDistance = v.findViewById(R.id.imgPlanetDistance)
            ftvPlanetDistance = v.findViewById(R.id.ftvPlanetDistance)
            txtPlanetDistance = v.findViewById(R.id.txtPlanetDistance)
            txtDiameter = v.findViewById(R.id.txtDiameter)
            txtOrbitalPeriod = v.findViewById(R.id.txtOrbitalPeriod)

        }

        fun bind(item: Moons, listener: OnItemClickListener?, context: Context) {
            setData(item, context)

            rlContent.setOnClickListener {
                // lnMoons.setBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.id), null))
                focusedItem = layoutPosition
               // listener!!.onItemClick(item, it)
            }

        }

        fun setData(item: Moons, context: Context){
            txtTitle.text = item.title
            txtYear.text = item.discovered
            imgPlanetDistance.setImageResource(PlanetImgHelper.getImageByPlanetID(item.planet_id))
            ftvPlanetDistance.setTextColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.planet_id)))
            txtPlanetDistance.text = item.distance.toString()
            txtDiameter.text = item.diameter.toString()
            txtOrbitalPeriod.text = item.orbital_period.toString()
        }

    }

    companion object {
        private var focusedItem = -1
    }

}
