package com.senlasy.planetz.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.senlasy.planetz.R
import com.senlasy.planetz.adapter.DetailViewAdapter
import com.senlasy.planetz.adapter.MainViewAdapter
import com.senlasy.planetz.fragment.PlanetDetatilFragment
import com.senlasy.planetz.fragment.PlanetFactsFragment
import com.senlasy.planetz.fragment.PlanetMoonFragment
import com.senlasy.planetz.model.Planet
import com.senlasy.planetz.mutility.PlanetImgHelper

class DetailActivity : AppCompatActivity(),
        PlanetDetatilFragment.OnDetailFragmentInteractionListener,
        PlanetMoonFragment.OnFragmentInteractionListener,
        PlanetFactsFragment.OnFragmentInteractionListener {

    lateinit var imgPlanetProfile : ImageView
    lateinit var imgPlanetFact : ImageView
    lateinit var imgPlanetMoon : ImageView
    lateinit var imgProfileBar : ImageView
    lateinit var imgFactBar : ImageView
    lateinit var imgMoonBar : ImageView

    lateinit var viewpager : ViewPager
    var pgAdapter : DetailViewAdapter? = null
    var mplanet : Planet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        imgPlanetProfile = findViewById(R.id.imgPlanetProfile)
        imgPlanetFact = findViewById(R.id.imgPlanetFact)
        imgPlanetMoon = findViewById(R.id.imgPlanetMoon)
        imgProfileBar = findViewById(R.id.imgProfileBar)
        imgFactBar = findViewById(R.id.imgFactBar)
        imgMoonBar = findViewById(R.id.imgMoonBar)

        findViewById<ImageButton>(R.id.imgbtnBack).setOnClickListener {
            backToYou()
        }

        planetFromIntent()

        viewpager = findViewById(R.id.viewpager)
        pgAdapter = DetailViewAdapter(supportFragmentManager, 0, mplanet!!)
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
                        imgProfileBar.visibility = View.VISIBLE
                        imgFactBar.visibility = View.GONE
                        imgMoonBar.visibility = View.GONE
                    }
                    1 -> {
                        imgProfileBar.visibility = View.GONE
                        imgFactBar.visibility = View.VISIBLE
                        imgMoonBar.visibility = View.GONE
                    }
                    2 -> {
                        imgProfileBar.visibility = View.GONE
                        imgFactBar.visibility = View.GONE
                        imgMoonBar.visibility = View.VISIBLE
                    }
                }
            }
        })

        imgPlanetProfile.setOnClickListener {
            viewpager.setCurrentItem(0, true)
        }

        imgPlanetFact.setOnClickListener {
            viewpager.setCurrentItem(1, true)
        }

        imgPlanetMoon.setOnClickListener {
            viewpager.setCurrentItem(2, true)
        }

    }

    fun planetFromIntent() {
        if(intent.hasExtra("planet")){
            mplanet = intent.getParcelableExtra("planet")
            imgPlanetProfile.setImageResource(PlanetImgHelper.getImageByPlanetID(mplanet!!.id))
        } else {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backToYou()
    }

    fun backToYou() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

}
