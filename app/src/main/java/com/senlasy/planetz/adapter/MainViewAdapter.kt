package com.senlasy.planetz.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.senlasy.planetz.fragment.*
import java.util.*

class MainViewAdapter(fm: FragmentManager, behavior : Int) : FragmentPagerAdapter(fm, behavior) {

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return MainFragment.newInstance("Planetz")
            1 -> return TheSunFragment.newInstance("Planetz")
            2 -> return TheMoonFragment.newInstance("Planetz")
            3 -> return FavFragment.newInstance("Planetz")
            4 -> return FactsFragment.newInstance("Planetz")
            else -> return MainFragment.newInstance("Plantz")
        }
    }

    override fun getCount(): Int {
        return 5
    }

    override fun getItemPosition(`object`: Any): Int {
       return POSITION_NONE
    }

}