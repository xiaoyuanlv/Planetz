package com.senlasy.planetz.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.senlasy.planetz.R
import com.senlasy.planetz.adapter.MainViewAdapter
import com.senlasy.planetz.fragment.*

class MainActivity : AppCompatActivity(),
    MainFragment.OnMainFragmentInteractionListener,
    TheSunFragment.OnFragmentInteractionListener,
    TheMoonFragment.OnFragmentInteractionListener,
    FactsFragment.OnFragmentInteractionListener,
    FavFragment.OnFragmentInteractionListener {

    lateinit var viewpager : ViewPager
    var pgAdapter : MainViewAdapter? = null

    lateinit var bar_home : View
    lateinit var bar_sun : View
    lateinit var bar_moon : View
    lateinit var bar_fav : View
    lateinit var bar_fact : View

    lateinit var imgHome  : ImageView
    lateinit var imgSun  : ImageView
    lateinit var imgMoon  : ImageView
    lateinit var imgPlanetGP  : ImageView
    lateinit var imgFact  : ImageView

    var doubleBackToExitPressedOnce : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bar_home = findViewById(R.id.bar_home)
        bar_sun = findViewById(R.id.bar_sun)
        bar_moon = findViewById(R.id.bar_moon)
        bar_fav = findViewById(R.id.bar_fav)
        bar_fact = findViewById(R.id.bar_fact)

        imgHome = findViewById(R.id.imgHome)
        imgSun = findViewById(R.id.imgSun)
        imgMoon = findViewById(R.id.imgMoon)
        imgPlanetGP = findViewById(R.id.imgPlanetGP)
        imgFact = findViewById(R.id.imgFact)


        viewpager = findViewById(R.id.viewpager)
        pgAdapter = MainViewAdapter(supportFragmentManager,0)
        viewpager.adapter = pgAdapter!!
        viewpager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
               when(position){
                   0 -> {
                       bar_home.setBackgroundColor(resources.getColor(R.color.colorAccent, null))
                       bar_sun.setBackgroundColor(resources.getColor(android.R.color.white, null))
                       bar_moon.setBackgroundColor(resources.getColor(android.R.color.white, null))
                       bar_fav.setBackgroundColor(resources.getColor(android.R.color.white, null))
                       bar_fact.setBackgroundColor(resources.getColor(android.R.color.white, null))
                   }
                   1 -> {
                       bar_home.setBackgroundColor(resources.getColor(android.R.color.white, null))
                       bar_sun.setBackgroundColor(resources.getColor(R.color.colorAccent, null))
                       bar_moon.setBackgroundColor(resources.getColor(android.R.color.white, null))
                       bar_fav.setBackgroundColor(resources.getColor(android.R.color.white, null))
                       bar_fact.setBackgroundColor(resources.getColor(android.R.color.white, null))
                   }
                   2 -> {
                       bar_home.setBackgroundColor(resources.getColor(android.R.color.white, null))
                       bar_sun.setBackgroundColor(resources.getColor(android.R.color.white, null))
                       bar_moon.setBackgroundColor(resources.getColor(R.color.colorAccent, null))
                       bar_fav.setBackgroundColor(resources.getColor(android.R.color.white, null))
                       bar_fact.setBackgroundColor(resources.getColor(android.R.color.white, null))
                   }
                   3 -> {
                       bar_home.setBackgroundColor(resources.getColor(android.R.color.white, null))
                       bar_sun.setBackgroundColor(resources.getColor(android.R.color.white, null))
                       bar_moon.setBackgroundColor(resources.getColor(android.R.color.white, null))
                       bar_fav.setBackgroundColor(resources.getColor(R.color.colorAccent, null))
                       bar_fact.setBackgroundColor(resources.getColor(android.R.color.white, null))
                       viewpager.adapter!!.notifyDataSetChanged()
                   }
                   4 -> {
                       bar_home.setBackgroundColor(resources.getColor(android.R.color.white, null))
                       bar_sun.setBackgroundColor(resources.getColor(android.R.color.white, null))
                       bar_moon.setBackgroundColor(resources.getColor(android.R.color.white, null))
                       bar_fav.setBackgroundColor(resources.getColor(android.R.color.white, null))
                       bar_fact.setBackgroundColor(resources.getColor(R.color.colorAccent, null))
                       viewpager.adapter!!.notifyDataSetChanged()
                   }
               }
            }
        })

        imgHome.setOnClickListener {
            viewpager.setCurrentItem(0, true)
        }

        imgSun.setOnClickListener {
            viewpager.setCurrentItem(1, true)
        }

        imgMoon.setOnClickListener {
            viewpager.setCurrentItem(2, true)
        }

        imgPlanetGP.setOnClickListener {
            viewpager.setCurrentItem(3, true)
        }

        imgFact.setOnClickListener {
            viewpager.setCurrentItem(4, true)
        }

    }

    fun setMainFragment(){
//        val  mainFragment = MainFragment.newInstance("PLANETS")

//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.frameContent, mainFragment, "MainFrag")
//            .addToBackStack(null)
//            .commit()
    }


    /******************** Fragment Interaction ****************/

    override fun onMainFragmentInteraction(uri: Uri) {

    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        Handler().postDelayed(Runnable() {
            doubleBackToExitPressedOnce = false
        }, 2000)

    }
}
