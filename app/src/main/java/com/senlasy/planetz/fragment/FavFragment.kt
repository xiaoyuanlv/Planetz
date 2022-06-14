package com.senlasy.planetz.fragment

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.senlasy.Stringz.adapter.FilterAdapter

import com.senlasy.planetz.R
import com.senlasy.planetz.adapter.PlanetFactAdapter
import com.senlasy.planetz.adapter.PlanetIconAdapter
import com.senlasy.planetz.database.DBHelper
import com.senlasy.planetz.model.Planet
import com.senlasy.planetz.model.PlanetFacts
import com.senlasy.planetz.mutility.PlanetImgHelper
import java.lang.Exception


private const val ARG_TITLE = "title"




class FavFragment : Fragment() {
    private var title: String? = null
    private var listener: OnFragmentInteractionListener? = null

    lateinit var rcyList  : RecyclerView
    lateinit var imgbtnFilter : ImageButton
    lateinit var imgFilterPlanet  :ImageView
    lateinit var txtEmpty  : TextView

    lateinit var progressBar : ProgressBar

    var filterPlanetId : Int = 0

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
        var view =  inflater.inflate(R.layout.fragment_fav, container, false)

        rcyList = view.findViewById(R.id.rcyList)
        rcyList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        txtEmpty = view.findViewById(R.id.txtEmpty)
        imgbtnFilter = view.findViewById(R.id.imgbtnFilter)
        imgFilterPlanet = view.findViewById(R.id.imgFilterPlanet)
        progressBar = view.findViewById(R.id.progressBar)

        setSearchUI()

        imgbtnFilter.setOnClickListener {
            showFilterDialog()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun setSearchUI(){
        if(filterPlanetId > 0){
            imgFilterPlanet.setImageResource(PlanetImgHelper.getImageByPlanetID(filterPlanetId))
        } else {
            imgFilterPlanet.setImageDrawable(null)
        }
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
            FavFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                }
            }
    }

    fun showFilterDialog() {

        try {
            var dbHelper = DBHelper(requireContext())
            var planetList = dbHelper.getAllPlanet("distance")

            if (planetList.isNotEmpty()) {

                var planetIconAdapter = PlanetIconAdapter(planetList.toMutableList(), R.layout.item_planet_icon, requireContext())

                val viewGroup: ViewGroup = requireActivity().findViewById(android.R.id.content)
                val dialogView: View =
                    LayoutInflater.from(activity).inflate(R.layout.dialog_filter, viewGroup, false)

                val rcyList: RecyclerView = dialogView.findViewById(R.id.rcyList)
                rcyList.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                rcyList.adapter = planetIconAdapter

                val btnClose = dialogView.findViewById<Button>(R.id.btnClose)
                val btnClearFilter = dialogView.findViewById<Button>(R.id.btnClearFilter)
                val txtTitle = dialogView.findViewById<TextView>(R.id.txtTitle)
                val lnFilterAll = dialogView.findViewById<LinearLayout>(R.id.lnFilterAll)
                val txtSelectedTitle = dialogView.findViewById<TextView>(R.id.txtSelectedTitle)

                if(filterPlanetId > 0){
                    val selectedPlanet = dbHelper.getPlanet(filterPlanetId)
                    txtSelectedTitle.text = selectedPlanet!!.title
                } else {
                    txtSelectedTitle.text = ""
                }

                txtTitle.text = "Choose ~"

                val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()

                planetIconAdapter.setOnItemListener(object : PlanetIconAdapter.OnItemClickListener{
                    override fun onItemClick(item: Planet, view: View) {
                        filterPlanetId = item.id
                        progressBar.visibility = View.VISIBLE
                        getData()
                        alertDialog.dismiss()
                    }
                })

                btnClearFilter.setOnClickListener {
                    filterPlanetId = 0
                    progressBar.visibility = View.VISIBLE
                    getData()
                    alertDialog.dismiss()
                }


                btnClose.setOnClickListener {
                    alertDialog.dismiss()
                }

                alertDialog.window!!.setBackgroundDrawableResource(R.drawable.dialog_bg)
                alertDialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                alertDialog.show()

            }

        } catch (e : Exception){
            Log.e("QueueAdminFragment", e.message.toString())
        }
    }

    fun getData() {


        var dbHelper = DBHelper(requireContext())
        var lstList = dbHelper.getFavPlanetFact(filterPlanetId)
        var adapter = PlanetFactAdapter(lstList.toMutableList(), com.senlasy.planetz.R.layout.item_fact, requireContext())

        adapter.setOnItemListener(object : PlanetFactAdapter.OnItemClickListener{
            override fun onFavItemClick(item: PlanetFacts, view: TextView) {
                val dbHelper2 = DBHelper(activity!!)
                dbHelper2.updateFavPlanet(item.id, 0)
                adapter.remove(item)
                adapter.notifyDataSetChanged()

                val lstList = dbHelper.getFavPlanetFact(0)
                if(lstList.isNotEmpty()){
                    txtEmpty.visibility = View.GONE
                    rcyList.visibility = View.VISIBLE
                } else {
                    txtEmpty.visibility = View.VISIBLE
                    rcyList.visibility = View.GONE
                }
            }

            override fun onItemClick(item: PlanetFacts, view: View) {
                Toast.makeText(activity!!, item.description.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        rcyList.adapter = adapter

        if(filterPlanetId > 0){
            imgFilterPlanet.visibility = View.VISIBLE
            imgFilterPlanet.setImageResource(PlanetImgHelper.getImageByPlanetID(filterPlanetId))
        } else {
            imgFilterPlanet.visibility = View.GONE
        }

        val lstList2 = dbHelper.getFavPlanetFact(0)
        if(lstList2.isNotEmpty()){
            txtEmpty.visibility = View.GONE
            rcyList.visibility = View.VISIBLE
        } else {
            txtEmpty.visibility = View.VISIBLE
            rcyList.visibility = View.GONE
        }

        progressBar.visibility = View.GONE

    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            getData()
        }, 1000)

    }




}
