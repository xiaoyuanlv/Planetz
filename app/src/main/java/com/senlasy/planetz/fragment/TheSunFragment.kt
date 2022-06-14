package com.senlasy.planetz.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.senlasy.planetz.R
import com.senlasy.planetz.adapter.SunFactAdapter
import com.senlasy.planetz.database.DBHelper
import com.senlasy.planetz.model.SunFacts

private const val ARG_TITLE = "title"

class TheSunFragment : Fragment() {
    private var title: String? = null
    private var listener: OnFragmentInteractionListener? = null

    lateinit var txtAge : TextView
    lateinit var txtType : TextView
    lateinit var txtDiameter : TextView
    lateinit var txtMass : TextView
    lateinit var txtCircumferenceEquator : TextView
    lateinit var txtTemperature : TextView

    lateinit var imgbtnInfo : ImageButton
    lateinit var imgbtnFacts  : ImageButton
    lateinit var imgInfoStatus : ImageView
    lateinit var imgSunFactStatus : ImageView

    lateinit var rcySunFact : RecyclerView
    lateinit var rlSunCard : RelativeLayout

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
        val view =  inflater.inflate(R.layout.fragment_the_sun, container, false)

        txtAge = view.findViewById(R.id.txtAge)
        txtType = view.findViewById(R.id.txtType)
        txtDiameter = view.findViewById(R.id.txtDiameter)
        txtMass = view.findViewById(R.id.txtMass)
        txtCircumferenceEquator = view.findViewById(R.id.txtCircumferenceEquator)
        txtTemperature = view.findViewById(R.id.txtTemperature)

        imgbtnInfo = view.findViewById(R.id.imgbtnInfo)
        imgbtnFacts = view.findViewById(R.id.imgbtnFacts)
        imgInfoStatus = view.findViewById(R.id.imgInfoStatus)
        imgSunFactStatus = view.findViewById(R.id.imgSunFactStatus)
        rcySunFact = view.findViewById(R.id.rcySunFact)
        rcySunFact.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        rlSunCard = view.findViewById(R.id.rlSunCard)

        rlSunCard.visibility = View.VISIBLE
        rcySunFact.visibility = View.GONE

        imgInfoStatus.visibility = View.VISIBLE
        imgSunFactStatus.visibility = View.GONE

        imgbtnFacts.setOnClickListener {
            rlSunCard.visibility = View.GONE
            rcySunFact.visibility = View.VISIBLE

            imgInfoStatus.visibility = View.GONE
            imgSunFactStatus.visibility = View.VISIBLE
        }

        imgbtnInfo.setOnClickListener {
            rlSunCard.visibility = View.VISIBLE
            rcySunFact.visibility = View.GONE

            imgInfoStatus.visibility = View.VISIBLE
            imgSunFactStatus.visibility = View.GONE
        }

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
     //   fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String) =
            TheSunFragment().apply {
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
        var dbHelper = DBHelper(requireContext())
        val thesun = dbHelper.getTheSun()
        if(thesun != null) {
            txtAge.text = thesun.the_age.toString()
            txtType.text = thesun.the_type.toString()
            txtDiameter.text = thesun.the_diameter.toString()
            txtMass.text = thesun.the_mass.toString()
            txtCircumferenceEquator.text = thesun.the_circumference_at_equator.toString()
            txtTemperature.text = thesun.surface_temperature.toString()
        }

        val thesunfacts = dbHelper.getSunFacts()
        if(thesunfacts.isNotEmpty()){
            var factAdapter = SunFactAdapter(thesunfacts.toMutableList(), R.layout.item_fact, requireContext())
            factAdapter.setOnItemListener(object : SunFactAdapter.OnItemClickListener{
                override fun onFavItemClick(item: SunFacts, view: TextView) {

                }

                override fun onItemClick(item: SunFacts, view: View) {
                    Toast.makeText(activity!!, item.description.toString(), Toast.LENGTH_SHORT).show()
                }
            })
            rcySunFact.adapter = factAdapter
        }

    }
}
