package com.senlasy.planetz.model

import android.os.Parcel
import android.os.Parcelable

class PlanetFacts() : Parcelable {
    var id : Int = 0
    var description : String? = ""
    var planet_id : Int = 0
    var fav : Int = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        description = parcel.readString()
        planet_id = parcel.readInt()
        fav = parcel.readInt()
    }


    constructor(id : Int, description: String?, planet_id : Int, fav : Int) : this() {
        this.id = id
        this.description = description
        this.planet_id = planet_id
        this.fav = fav
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(description)
        parcel.writeInt(planet_id)
        parcel.writeInt(fav)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlanetFacts> {
        override fun createFromParcel(parcel: Parcel): PlanetFacts {
            return PlanetFacts(parcel)
        }

        override fun newArray(size: Int): Array<PlanetFacts?> {
            return arrayOfNulls(size)
        }
    }


}