package com.senlasy.planetz.fragment

import android.content.Context
import android.content.Intent
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
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.senlasy.Stringz.adapter.FilterAdapter

import com.senlasy.planetz.R
import com.senlasy.planetz.activity.DetailActivity
import com.senlasy.planetz.adapter.PlanetAdapter
import com.senlasy.planetz.database.DBHelper
import com.senlasy.planetz.model.Planet
import com.senlasy.planetz.model.PlanetCategory
import java.lang.Exception

private const val ARG_TITLE = "TITLE"


class MainFragment : Fragment(), PlanetAdapter.OnItemClickListener {


    private var title: String? = null
    private var listener: OnMainFragmentInteractionListener? = null

    lateinit var rcyPlanet : RecyclerView

    lateinit var ftvSort : TextView
    lateinit var lnSort : LinearLayout
    lateinit var imgSort : ImageView

    lateinit var ftvCategory : TextView
    lateinit var rlFilter : RelativeLayout
    lateinit var cardFilterStatus  : CardView

    lateinit var imgbtnInfo : ImageButton

    var sortBy = "distance"
    var filterCategory = 0

    var lstPlanet : MutableList<Planet>? = null
    var lstCategory : MutableList<PlanetCategory> = ArrayList()
    var adapter : PlanetAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_TITLE)
        }
    }

    override fun onResume() {
        super.onResume()
        getPlanetCategory()
        setUI()
        getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)


        rcyPlanet = view.findViewById(R.id.rcyPlanet)
        rcyPlanet.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        ftvSort = view.findViewById(R.id.ftvSort)
        lnSort = view.findViewById(R.id.lnSort)
        imgSort = view.findViewById(R.id.imgSort)
        rlFilter = view.findViewById(R.id.rlFilter)
        ftvCategory = view.findViewById(R.id.ftvCategory)
        cardFilterStatus = view.findViewById(R.id.cardFilterStatus)
        imgbtnInfo = view.findViewById(R.id.imgbtnInfo)

        imgbtnInfo.setOnClickListener {
            try {

                val viewGroup: ViewGroup = requireActivity().findViewById(android.R.id.content)
                val dialogView: View = LayoutInflater.from(activity).inflate(R.layout.app_info_dialog, viewGroup, false)


                val btnClose = dialogView.findViewById<Button>(R.id.btnClose)

                val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()


                btnClose.setOnClickListener {
                    alertDialog.dismiss()
                }

                alertDialog.window!!.setBackgroundDrawableResource(R.drawable.dialog_bg)
                alertDialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                alertDialog.show()
            }catch (e : Exception){
                Log.e("MainFragment",e.message.toString())
            }

        }

        rlFilter.setOnClickListener {
            showFilterDialog("Category")
            ftvCategory.setTextColor(requireContext().resources.getColor(R.color.colorAccent, null))
            Handler().postDelayed(Runnable {
                ftvCategory.setTextColor(requireActivity().resources.getColor(android.R.color.white, null))
            }, 1000)
        }


        lnSort.setOnClickListener {
            showFilterDialog("SortBy")
            ftvSort.setTextColor(requireContext().resources.getColor(R.color.colorAccent, null))
            Handler().postDelayed(Runnable {
                ftvSort.setTextColor(requireContext().resources.getColor(android.R.color.white, null))
            }, 1000)
        }


        return  view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMainFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnMainFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnMainFragmentInteractionListener {
        fun onMainFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                }
            }
    }

    fun getData() {
        var dbHelper = DBHelper(requireContext())

        if(filterCategory > 0) {
            lstPlanet = dbHelper.getPlanetByCategory(filterCategory, sortBy).toMutableList()
        } else {
            lstPlanet = dbHelper.getAllPlanet(sortBy).toMutableList()
        }
        setAdapter()
    }

    fun getPlanetCategory() {
        val dbHelper = DBHelper(requireContext())
        if(lstCategory.size == 0) {
            lstCategory = dbHelper.getAllPlanetCategory().toMutableList()
        }
    }

    fun setUI() {
        if(filterCategory > 0){
            cardFilterStatus.visibility = View.VISIBLE
        } else {
            cardFilterStatus.visibility = View.GONE
        }

        if(sortBy.trim().equals("distance", true)){
            imgSort.setImageResource(R.mipmap.distance)
        } else {
            imgSort.setImageResource(R.mipmap.diameter)
        }
        imgSort.invalidate()
    }

    fun setAdapter(){
        rcyPlanet.adapter = null
        adapter = null
        adapter = PlanetAdapter(lstPlanet!!, R.layout.item_planet, requireContext(), rcyPlanet)
        adapter!!.setOnItemListener(this)
        rcyPlanet.adapter = adapter

    }

    override fun onItemClick(item: Planet, view: View) {
        requireActivity().finish()

        val intent = Intent(requireContext(), DetailActivity::class.java)
         intent.putExtra("planet", item)
        requireContext().startActivity(intent)
    }


    private fun showFilterDialog(title: String){
        try {

            val viewGroup: ViewGroup = requireActivity().findViewById(android.R.id.content)
            val dialogView: View = LayoutInflater.from(activity).inflate(R.layout.dialog_filter, viewGroup, false)

            val rcyList : RecyclerView = dialogView.findViewById(R.id.rcyList)
            rcyList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

            val btnClose = dialogView.findViewById<Button>(R.id.btnClose)
            val btnClearFilter = dialogView.findViewById<Button>(R.id.btnClearFilter)
            val txtTitle = dialogView.findViewById<TextView>(R.id.txtTitle)
            val lnFilterAll = dialogView.findViewById<LinearLayout>(R.id.lnFilterAll)
            val txtSelectedTitle = dialogView.findViewById<TextView>(R.id.txtSelectedTitle)
            txtTitle.text = title

            val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogView).create()

            var filterArr : MutableList<String> = ArrayList()
            if(title.equals("SortBy", true)){
                btnClearFilter.visibility = View.GONE
                lnFilterAll.visibility = View.GONE
                filterArr.add("Distance from the Sun")
                filterArr.add("Diameter")

                if(sortBy.trim().equals("diameter", true)){
                    txtSelectedTitle.text = "Diamter"
                } else {
                    txtSelectedTitle.text = "Distance from the Sun"
                }

            } else if(title.equals("Category", true)) {
                if(filterCategory > 0){
                    txtSelectedTitle.text = lstCategory[filterCategory - 1].title
                } else {
                    txtSelectedTitle.text = "All"
                }
                btnClearFilter.visibility = View.VISIBLE
                btnClearFilter.setOnClickListener {
                    filterCategory = 0
                    cardFilterStatus.visibility = View.GONE
                    setUI()
                    getData()
                    alertDialog.dismiss()
                }
                getPlanetCategory()

                for (category in lstCategory){
                    filterArr.add(category.title.toString())
                }
            }

            val filterAdapter = FilterAdapter(filterArr, R.layout.item_filter, requireContext())
            rcyList.adapter = filterAdapter
            filterAdapter.setOnItemListener(object : FilterAdapter.OnItemClickListener{
                override fun onItemClick(item: String, view: View, position :Int) {
                    alertDialog.dismiss()
                    if(title.equals("SortBy",true)){
                        if(item.equals("diameter", true)){
                            sortBy = "diameter"
                        } else {
                            sortBy = "distance"
                        }
                    } else if(title.equals("Category", true)){
                            filterCategory = position + 1
                            cardFilterStatus.visibility = View.VISIBLE
                    }
                    setUI()
                    getData()
                }
            })
            btnClose.setOnClickListener {
                alertDialog.dismiss()
            }

            alertDialog.window!!.setBackgroundDrawableResource(R.drawable.dialog_bg)
            alertDialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
            alertDialog.show()
        }catch (e : Exception){
            Log.e("MainFragment",e.message.toString())
        }

    }

}
