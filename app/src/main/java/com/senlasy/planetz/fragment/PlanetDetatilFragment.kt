package com.senlasy.planetz.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.senlasy.PlanetProfilez.adapter.PlanetProfileProfileAdapter
import com.senlasy.planetz.R
import com.senlasy.planetz.database.DBHelper
import com.senlasy.planetz.model.Planet
import com.senlasy.planetz.model.PlanetProfile
import com.senlasy.planetz.mutility.PlanetImgHelper
import org.w3c.dom.Text

private const val ARG_PLANET = "planet"


class PlanetDetatilFragment : Fragment(), PlanetProfileProfileAdapter.OnItemClickListener {

    private var planet: Planet? = null
    private var listener: OnDetailFragmentInteractionListener? = null

    lateinit var imgPlanet : ImageView
    lateinit var txtTitle : TextView
    lateinit var txtDistance : TextView
    lateinit var txtDiameter : TextView
    lateinit var bar : View
    lateinit var rcyProfile : RecyclerView
    lateinit var imgProfileImage : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            planet = it.getParcelable(ARG_PLANET)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_planet_detatil, container, false)
        imgPlanet = view.findViewById(R.id.imgPlanet)
        txtTitle = view.findViewById(R.id.txtTitle)
        txtDistance = view.findViewById(R.id.txtDistance)
        txtDiameter = view.findViewById(R.id.txtDiameter)
        bar = view.findViewById(R.id.bar)
        imgProfileImage = view.findViewById(R.id.imgProfileImage)

        imgProfileImage.setImageResource(PlanetImgHelper.getBackgroundImageByPlanetID(planet!!.id))
        txtTitle.text = planet!!.title
        txtDiameter.text = planet!!.diameter_km.toString() + " km"
        txtDistance.text = planet!!.distance_thesun_km.toString() + " km"
        imgPlanet.setImageResource(PlanetImgHelper.getImageByPlanetID(planet!!.id))
        bar.setBackgroundColor(resources.getColor(PlanetImgHelper.getColorbyPlanetID(planet!!.id),null))

        rcyProfile = view.findViewById(R.id.rcyProfile)
        rcyProfile.layoutManager = LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnDetailFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnDetailFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onResume() {
        super.onResume()
        getProfileData()
    }

    fun getProfileData(){
        val dbHelper = DBHelper(activity!!)
        val p = dbHelper.getPlanetProfile(planet_id = planet!!.id)
        if(p != null){
            val lst = ArrayList<PlanetProfile>()
            lst.add(p)
            val adpter = PlanetProfileProfileAdapter(lst, R.layout.item_planet_profile, activity!!)
            adpter.setOnItemListener(this)
            rcyProfile.adapter = adpter
        }
    }

    override fun onItemClick(item: PlanetProfile, view: View) {
        Toast.makeText(activity!!, planet!!.title, Toast.LENGTH_SHORT).show()
    }


    interface OnDetailFragmentInteractionListener {
       // fun onDetailFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(planet: Planet) =
            PlanetDetatilFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PLANET, planet)
                }
            }
    }


}
