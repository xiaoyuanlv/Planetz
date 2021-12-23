package com.senlasy.planetz.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.senlasy.planetz.fragment.*
import com.senlasy.planetz.model.Planet

class DetailViewAdapter (fm: FragmentManager, beh : Int, var mplanet: Planet) : FragmentPagerAdapter(fm, beh) {

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return PlanetDetatilFragment.newInstance(planet = mplanet)
            1 -> return PlanetFactsFragment.newInstance(planet = mplanet)
            2 -> return PlanetMoonFragment.newInstance(planet = mplanet)
            else -> return PlanetDetatilFragment.newInstance(planet = mplanet)
        }
    }

    override fun getCount(): Int {
        return 3
    }

}