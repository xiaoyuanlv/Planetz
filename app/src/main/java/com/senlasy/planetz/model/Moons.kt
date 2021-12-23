package com.senlasy.planetz.model

import android.os.Parcel
import android.os.Parcelable

class Moons() : Parcelable {
    var id : Int = 0
    var title : String? = ""
    var discovered : String? = ""
    var distance : String? = ""
    var diameter : String? = ""
    var orbital_period : String? = ""
    var planet_id : Int = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        title = parcel.readString()
        discovered = parcel.readString()
        distance = parcel.readString()
        diameter = parcel.readString()
        orbital_period = parcel.readString()
        planet_id = parcel.readInt()
    }


    constructor(id: Int, title : String?, discovered  : String?, distance : String?, diameter : String?, orbital_period : String?, planet_id : Int) : this(){
        this.id = id
        this.title = title
        this.discovered = discovered
        this.distance = distance
        this.diameter = diameter
        this.orbital_period= orbital_period
        this.planet_id = planet_id
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(discovered)
        parcel.writeString(distance)
        parcel.writeString(diameter)
        parcel.writeString(orbital_period)
        parcel.writeInt(planet_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Moons> {
        override fun createFromParcel(parcel: Parcel): Moons {
            return Moons(parcel)
        }

        override fun newArray(size: Int): Array<Moons?> {
            return arrayOfNulls(size)
        }
    }


}