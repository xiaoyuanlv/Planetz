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

import com.senlasy.planetz.R
import com.senlasy.planetz.database.DBHelper
import com.senlasy.planetz.mutility.PlanetImgHelper


private const val ARG_TITLE = "title"

class TheMoonFragment : Fragment() {

    private var title: String? = null
    private var listener: OnFragmentInteractionListener? = null

    lateinit var txtAge : TextView
    lateinit var txtDiameter : TextView
    lateinit var txtMass : TextView
    lateinit var txtCircumferenceEquator : TextView
    lateinit var txtTemperature : TextView

    lateinit var mainView : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_the_moon, container, false)

        txtAge = view.findViewById(R.id.txtAge)
        txtDiameter = view.findViewById(R.id.txtDiameter)
        txtMass = view.findViewById(R.id.txtMass)
        txtCircumferenceEquator = view.findViewById(R.id.txtCircumferenceEquator)
        txtTemperature = view.findViewById(R.id.txtTemperature)

        mainView = view
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
//        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance(title: String) =
            TheMoonFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    fun getData() {
        var dbHelper = DBHelper(activity!!)
        val themoon = dbHelper.getTheMoon()
        if(themoon != null) {
            txtAge.text = themoon.the_age.toString()
            txtDiameter.text = themoon.the_diameter.toString()
            txtMass.text = themoon.the_mass.toString()
            txtCircumferenceEquator.text = themoon.the_circumference_at_equator.toString()
            txtTemperature.text = themoon.surface_temperature.toString()
        }

        val moonList = dbHelper.getMoonsList(3)
        if(moonList.isNotEmpty()) {
            mainView.findViewById<TextView>(R.id.txtTitle).text = moonList[0].title
            mainView.findViewById<TextView>(R.id.txtYear).text =  moonList[0].discovered
            mainView.findViewById<ImageView>(R.id.imgPlanetDistance).setImageResource(
                PlanetImgHelper.getImageByPlanetID(moonList[0].planet_id))
            mainView.findViewById<TextView>(R.id.ftvPlanetDistance).setTextColor(activity!!.resources.getColor(
                PlanetImgHelper.getColorbyPlanetID(moonList[0].planet_id) , null))
            mainView.findViewById<TextView>(R.id.txtPlanetDistance).text =  moonList[0].distance
            mainView.findViewById<TextView>(R.id.txtDiameter).text =  moonList[0].diameter
            mainView.findViewById<TextView>(R.id.txtOrbitalPeriod).text =  moonList[0].orbital_period
        }
    }
}
