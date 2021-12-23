package com.senlasy.planetz.mutility

import com.senlasy.planetz.R

class PlanetImgHelper {
    companion object{
        fun getTriangleArrowByPlanetID(id : Int) : Int{
            when(id){
                1 -> return R.drawable.mercury_triangle
                2 -> return R.drawable.venus_triangle
                3 -> return R.drawable.earth_triangle
                4 -> return R.drawable.mars_triangle
                5 -> return R.drawable.jupitar_triangle
                6 -> return R.drawable.saturn_triangle
                7 -> return R.drawable.uranus_triangle
                8 -> return R.drawable.neptune_triangle
                else -> {
                    return 0
                }
            }
        }

        fun getImageByPlanetID(id : Int) : Int{
            when(id){
                1 -> return R.mipmap.mercury
                2 -> return R.mipmap.venus
                3 -> return R.mipmap.earth
                4 -> return R.mipmap.mars
                5 -> return R.mipmap.jupiter
                6 -> return R.mipmap.saturn
                7 -> return R.mipmap.uranus
                8 -> return R.mipmap.neptune
                else -> {
                    return 0
                }
            }
        }

        fun getBackgroundImageByPlanetID(id : Int) : Int{
            when(id){
                1 -> return R.mipmap.mercury_bg
                2 -> return R.mipmap.venus_bg
                3 -> return R.mipmap.earth_bg
                4 -> return R.mipmap.mars_bg
                5 -> return R.mipmap.jupitar_bg
                6 -> return R.mipmap.saturn_bg
                7 -> return R.mipmap.uranus_bg
                8 -> return R.mipmap.neptune_bg
                else -> {
                    return 0
                }
            }
        }

        fun getColorbyPlanetID(id : Int) : Int {
            when(id){
                1 -> return R.color.mercury_color
                2 -> return R.color.venus_color
                3 -> return R.color.earth_color
                4 -> return R.color.mars_color
                5 -> return R.color.jupiter_color
                6 -> return R.color.saturn_color
                7 -> return R.color.uranus_color
                8 -> return R.color.neptune_color
                else -> {
                    return R.color.colorAccent
                }
            }
        }
    }
}