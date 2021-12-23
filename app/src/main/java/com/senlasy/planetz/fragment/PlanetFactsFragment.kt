package com.senlasy.planetz.fragment

import android.content.Context
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

import com.senlasy.planetz.R
import com.senlasy.planetz.adapter.PlanetFactAdapter
import com.senlasy.planetz.database.DBHelper
import com.senlasy.planetz.model.Planet
import com.senlasy.planetz.model.PlanetFacts
import com.senlasy.planetz.mutility.PlanetImgHelper
import info.androidhive.fontawesome.FontTextView

private const val ARG_PLANET = "planet"


class PlanetFactsFragment : Fragment(), PlanetFactAdapter.OnItemClickListener {


    private var planet: Planet? = null
    private var listener: OnFragmentInteractionListener? = null

    lateinit var imgPlanetProfile : ImageView
    lateinit var imgProfileImage : ImageView
    lateinit var rcyFact : RecyclerView
    lateinit var txtPlanetDescription  : TextView

    var factAdapter : PlanetFactAdapter? = null

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
        val view = inflater.inflate(R.layout.fragment_planet_facts, container, false)

        imgPlanetProfile = view.findViewById(R.id.imgPlanetProfile)
        imgProfileImage = view.findViewById(R.id.imgProfileImage)
        rcyFact = view.findViewById(R.id.rcyFact)

        imgPlanetProfile.setImageResource(PlanetImgHelper.getImageByPlanetID(planet!!.id))
        imgProfileImage.setImageResource(PlanetImgHelper.getBackgroundImageByPlanetID(planet!!.id))
        rcyFact.layoutManager = LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)

        txtPlanetDescription = view.findViewById(R.id.txtPlanetDescription)

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
       // fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(planet: Planet) =
            PlanetFactsFragment().apply {
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
        txtPlanetDescription.text = planet!!.description.toString()
        var dbHelper = DBHelper(activity!!)
        var plantFact = dbHelper.getPlanetFact(planet_id = planet!!.id)
        if(plantFact.isNotEmpty()) {
            factAdapter = PlanetFactAdapter(plantFact.toMutableList(), R.layout.item_fact, activity!!)
            factAdapter!!.setOnItemListener(this)
            rcyFact.adapter = factAdapter
        }
    }

    override fun onItemClick(item: PlanetFacts, view: View) {
        Toast.makeText(activity!!, item.description, Toast.LENGTH_SHORT).show()
    }

    override fun onFavItemClick(item: PlanetFacts, view: FontTextView) {
        if(item.fav == 1){
            view.setTextColor(resources.getColor(android.R.color.white, null))
            item.fav = 0
        } else {
            view.setTextColor(resources.getColor(R.color.colorAccent, null))
            item.fav = 1
        }

        val dbHelper = DBHelper(activity!!)
        if(dbHelper.updateFavPlanet(item.id, item.fav) > 0) {
            factAdapter!!.notifyDataSetChanged()
        }
        Toast.makeText(activity!!, item.description, Toast.LENGTH_SHORT).show()
    }


}
