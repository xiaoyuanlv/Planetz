package com.senlasy.PlanetProfilez.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.senlasy.planetz.R
import com.senlasy.planetz.model.PlanetProfile
import com.senlasy.planetz.mutility.PlanetImgHelper

class PlanetProfileProfileAdapter (
    var ItemList: MutableList<PlanetProfile>,
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
        fun onItemClick(item: PlanetProfile, view : View)
    }

    fun changeLayout(rowLayout : Int){
        this.rowLayout = rowLayout
    }

    fun addAll(item: List<PlanetProfile>) {
        ItemList.addAll(item)
        notifyDataSetChanged()
    }

    fun setData(lst: MutableList<PlanetProfile>){
        ItemList = lst
        notifyDataSetChanged()
    }

    fun remove(item: PlanetProfile) {
        val position = ItemList.indexOf(item)
        ItemList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        var vholder : RecyclerView.ViewHolder? = null
        if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
            vholder = PlanetProfileViewHolder(view)
        }
        return vholder as RecyclerView.ViewHolder
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PlanetProfileViewHolder) {

            holder.bind(ItemList[position], listener, context)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return  VIEW_TYPE_ITEM
    }


    fun setOnItemListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    class PlanetProfileViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var lnPlanetProfile: LinearLayout
        internal var txtMass: TextView
        internal var txtEquatorDiameter: TextView
        internal var txtPolarDiameter: TextView
        internal var txtEquatorCircumference: TextView
        internal var txtKnownMoons: TextView
        internal var txtNotableMoons: TextView
        internal var txtOrbitDistance: TextView
        internal var txtOrbitPeriod: TextView
        internal var txtSurfaceTemp: TextView
        internal var txtFirstRecord: TextView
        internal var txtRecordedBy: TextView


        internal var tri1 : ImageView
        internal var tri2 : ImageView
        internal var tri3 : ImageView
        internal var tri4 : ImageView
        internal var tri5 : ImageView
        internal var tri6 : ImageView
        internal var tri7 : ImageView
        internal var tri8 : ImageView
        internal var tri9 : ImageView
        internal var tri10 : ImageView
        internal var tri11 : ImageView


        internal var view1 : View
        internal var view2 : View
        internal var view3 : View
        internal var view4 : View
        internal var view5 : View
        internal var view6 : View
        internal var view7 : View
        internal var view8 : View
        internal var view9 : View
        internal var view10 : View
        internal var view11 : View


        init {

            lnPlanetProfile = v.findViewById(R.id.lnPlanetProfile)
            txtMass = v.findViewById(R.id.txtMass)
            txtEquatorDiameter = v.findViewById(R.id.txtEquatorDiameter)
            txtPolarDiameter = v.findViewById(R.id.txtPolarDiameter)
            txtEquatorCircumference = v.findViewById(R.id.txtEquatorCircumference)
            txtKnownMoons = v.findViewById(R.id.txtKnownMoons)
            txtNotableMoons = v.findViewById(R.id.txtNotableMoons)
            txtOrbitDistance = v.findViewById(R.id.txtOrbitDistance)
            txtOrbitPeriod = v.findViewById(R.id.txtOrbitPeriod)
            txtSurfaceTemp = v.findViewById(R.id.txtSurfaceTemp)
            txtFirstRecord = v.findViewById(R.id.txtFirstRecord)
            txtRecordedBy = v.findViewById(R.id.txtRecordedBy)

            tri1 = v.findViewById(R.id.tri1)
            tri2 = v.findViewById(R.id.tri2)
            tri3 = v.findViewById(R.id.tri3)
            tri4 = v.findViewById(R.id.tri4)
            tri5 = v.findViewById(R.id.tri5)
            tri6 = v.findViewById(R.id.tri6)
            tri7 = v.findViewById(R.id.tri7)
            tri8 = v.findViewById(R.id.tri8)
            tri9 = v.findViewById(R.id.tri9)
            tri10 = v.findViewById(R.id.tri10)
            tri11 = v.findViewById(R.id.tri11)

            view1 = v.findViewById(R.id.view1)
            view2 = v.findViewById(R.id.view2)
            view3 = v.findViewById(R.id.view3)
            view4 = v.findViewById(R.id.view4)
            view5 = v.findViewById(R.id.view5)
            view6 = v.findViewById(R.id.view6)
            view7 = v.findViewById(R.id.view7)
            view8 = v.findViewById(R.id.view8)
            view9 = v.findViewById(R.id.view9)
            view10 = v.findViewById(R.id.view10)
            view11 = v.findViewById(R.id.view11)

        }

        fun bind(item: PlanetProfile, listener: OnItemClickListener?, context: Context) {
            setData(item, context)

            lnPlanetProfile.setOnClickListener {
               // lnPlanetProfile.setBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.id), null))
                focusedItem = layoutPosition
                listener!!.onItemClick(item, it)
            }

        }

        fun setData(item: PlanetProfile, context: Context){
            view1.setBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.planet_id),null))
            view2.setBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.planet_id),null))
            view3.setBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.planet_id),null))
            view4.setBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.planet_id),null))
            view5.setBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.planet_id),null))
            view6.setBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.planet_id),null))
            view7.setBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.planet_id),null))
            view8.setBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.planet_id),null))
            view9.setBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.planet_id),null))
            view10.setBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.planet_id),null))
            view11.setBackgroundColor(context.resources.getColor(PlanetImgHelper.getColorbyPlanetID(item.planet_id),null))

            tri1.setImageResource(PlanetImgHelper.getTriangleArrowByPlanetID(item.planet_id))
            tri2.setImageResource(PlanetImgHelper.getTriangleArrowByPlanetID(item.planet_id))
            tri3.setImageResource(PlanetImgHelper.getTriangleArrowByPlanetID(item.planet_id))
            tri4.setImageResource(PlanetImgHelper.getTriangleArrowByPlanetID(item.planet_id))
            tri5.setImageResource(PlanetImgHelper.getTriangleArrowByPlanetID(item.planet_id))
            tri6.setImageResource(PlanetImgHelper.getTriangleArrowByPlanetID(item.planet_id))
            tri7.setImageResource(PlanetImgHelper.getTriangleArrowByPlanetID(item.planet_id))
            tri8.setImageResource(PlanetImgHelper.getTriangleArrowByPlanetID(item.planet_id))
            tri9.setImageResource(PlanetImgHelper.getTriangleArrowByPlanetID(item.planet_id))
            tri10.setImageResource(PlanetImgHelper.getTriangleArrowByPlanetID(item.planet_id))
            tri11.setImageResource(PlanetImgHelper.getTriangleArrowByPlanetID(item.planet_id))

            txtMass.text = item.mass.toString()
            txtEquatorDiameter.text = item.equatorial_diameter.toString()
            txtPolarDiameter.text = item.polar_diameter.toString()
            txtEquatorCircumference.text = item.equatorial_circumference.toString()
            txtKnownMoons.text = item.known_moons.toString()
            txtNotableMoons.text = item.notable_moons.toString()
            txtOrbitDistance.text = item.orbit_distance.toString()
            txtOrbitPeriod.text = item.orbit_period.toString()
            txtSurfaceTemp.text = item.surface_temperature.toString()
            txtFirstRecord.text = item.first_record.toString()
            txtRecordedBy.text = item.recorded_by.toString()

        }

    }

    companion object {
        private var focusedItem = -1
    }

}
