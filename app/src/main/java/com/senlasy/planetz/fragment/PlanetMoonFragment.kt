package com.senlasy.planetz.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.senlasy.planetz.R
import com.senlasy.planetz.adapter.MoonListAdapter
import com.senlasy.planetz.database.DBHelper
import com.senlasy.planetz.model.Planet
import com.senlasy.planetz.mutility.PlanetImgHelper
import org.w3c.dom.Text

private const val ARG_PLANET = "planet"

class PlanetMoonFragment : Fragment() {
    private var planet: Planet? = null
    private var listener: OnFragmentInteractionListener? = null

    lateinit var rlTheMoon : RelativeLayout
    lateinit var rcyMoon : RecyclerView
    lateinit var bar : View

    lateinit var txtAge : TextView
    lateinit var txtDiameter : TextView
    lateinit var txtMass : TextView
    lateinit var txtCircumferenceEquator : TextView
    lateinit var txtTemperature : TextView
    lateinit var txtEmpty : TextView

    lateinit var mainView : View

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
        val view = inflater.inflate(R.layout.fragment_planet_moon, container, false)
        mainView = view
        rlTheMoon = view.findViewById(R.id.rlTheMoon)
        rcyMoon = view.findViewById(R.id.rcyMoon)
        bar = view.findViewById(R.id.bar)

        txtAge = view.findViewById(R.id.txtAge)
        txtDiameter = view.findViewById(R.id.txtDiameter)
        txtMass = view.findViewById(R.id.txtMass)
        txtCircumferenceEquator = view.findViewById(R.id.txtCircumferenceEquator)
        txtTemperature = view.findViewById(R.id.txtTemperature)
        txtEmpty = view.findViewById(R.id.txtEmpty)

        bar.setBackgroundColor(requireActivity().resources.getColor(PlanetImgHelper.getColorbyPlanetID(planet!!.id), null))
        rcyMoon.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)

        return view
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        //fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(planet: Planet) =
            PlanetMoonFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PLANET, planet)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    fun getData() {

        var dbHelper = DBHelper(requireActivity())
        if(planet!!.id == 3){
            rlTheMoon.visibility = View.VISIBLE
            val themoon = dbHelper.getTheMoon()
            if(themoon != null) {
                txtAge.text = themoon.the_age.toString()
                txtDiameter.text = themoon.the_diameter.toString()
                txtMass.text = themoon.the_mass.toString()
                txtCircumferenceEquator.text = themoon.the_circumference_at_equator.toString()
                txtTemperature.text = themoon.surface_temperature.toString()
            }

            val moonList = dbHelper.getMoonsList(planet!!.id)
            if(moonList.isNotEmpty()) {
                mainView.findViewById<TextView>(R.id.txtTitle).text = moonList[0].title
                mainView.findViewById<TextView>(R.id.txtYear).text =  moonList[0].discovered
                mainView.findViewById<ImageView>(R.id.imgPlanetDistance).setImageResource(PlanetImgHelper.getImageByPlanetID(moonList[0].planet_id))
                mainView.findViewById<TextView>(R.id.ftvPlanetDistance).setTextColor(requireActivity().resources.getColor(PlanetImgHelper.getColorbyPlanetID(moonList[0].planet_id) , null))
                mainView.findViewById<TextView>(R.id.txtPlanetDistance).text =  moonList[0].distance
                mainView.findViewById<TextView>(R.id.txtDiameter).text =  moonList[0].diameter
                mainView.findViewById<TextView>(R.id.txtOrbitalPeriod).text =  moonList[0].orbital_period
            }

            txtEmpty.visibility = View.GONE
            rcyMoon.visibility  = View.GONE



        } else {
            rlTheMoon.visibility = View.GONE
            val moonList = dbHelper.getMoonsList(planet!!.id)
            if(moonList.isNotEmpty()) {
                val moonAdapter = MoonListAdapter(moonList.toMutableList(), R.layout.item_moon, requireActivity())
                rcyMoon.adapter= moonAdapter
                rcyMoon.visibility  = View.VISIBLE
                txtEmpty.visibility = View.GONE
            } else {
                rcyMoon.visibility  = View.GONE
                txtEmpty.visibility = View.VISIBLE
            }

        }


    }

}
