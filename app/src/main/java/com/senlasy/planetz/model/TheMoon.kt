package com.senlasy.planetz.model

import android.os.Parcel
import android.os.Parcelable

class TheMoon() : Parcelable {
    var the_age  : String? = ""
    var the_mass : String? = ""
    var the_diameter : String? = ""
    var the_circumference_at_equator : String? = ""
    var surface_temperature : String? = ""

    constructor(parcel: Parcel) : this() {
        the_age = parcel.readString()
        the_mass = parcel.readString()
        the_diameter = parcel.readString()
        the_circumference_at_equator = parcel.readString()
        surface_temperature = parcel.readString()
    }

    constructor(the_age :String?, the_mass : String?, the_diameter : String?, the_circumference_at_equator : String?, surface_temperature : String?) : this(){
        this.the_age = the_age
        this.the_mass = the_mass
        this.the_diameter = the_diameter
        this.the_circumference_at_equator = the_circumference_at_equator
        this.surface_temperature = surface_temperature
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(the_age)
        parcel.writeString(the_mass)
        parcel.writeString(the_diameter)
        parcel.writeString(the_circumference_at_equator)
        parcel.writeString(surface_temperature)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TheMoon> {
        override fun createFromParcel(parcel: Parcel): TheMoon {
            return TheMoon(parcel)
        }

        override fun newArray(size: Int): Array<TheMoon?> {
            return arrayOfNulls(size)
        }
    }


}