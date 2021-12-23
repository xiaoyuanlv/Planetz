package com.senlasy.planetz.model

import android.os.Parcel
import android.os.Parcelable

class TheSun() : Parcelable {
    var id  :Int = 0
    var the_age : String? = ""
    var the_type: String? = ""
    var the_mass: String? = ""
    var the_diameter: String? = ""
    var the_circumference_at_equator  : String? = ""
    var surface_temperature  :String? = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        the_age = parcel.readString()
        the_type = parcel.readString()
        the_mass = parcel.readString()
        the_diameter = parcel.readString()
        the_circumference_at_equator = parcel.readString()
        surface_temperature = parcel.readString()
    }

    constructor(id : Int, the_age  :String?, the_type : String?, the_mass : String?,
                the_diameter: String?, the_circumference_at_equator: String?, surface_temperature : String?) : this(){
        this.id = id
        this.the_age = the_age
        this.the_type = the_type
        this.the_mass = the_mass
        this.the_diameter = the_diameter
        this.the_circumference_at_equator = the_circumference_at_equator
        this.surface_temperature = surface_temperature
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(the_age)
        parcel.writeString(the_type)
        parcel.writeString(the_mass)
        parcel.writeString(the_diameter)
        parcel.writeString(the_circumference_at_equator)
        parcel.writeString(surface_temperature)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TheSun> {
        override fun createFromParcel(parcel: Parcel): TheSun {
            return TheSun(parcel)
        }

        override fun newArray(size: Int): Array<TheSun?> {
            return arrayOfNulls(size)
        }
    }
}