package com.senlasy.planetz.model

import android.os.Parcel
import android.os.Parcelable

class Planet() : Parcelable {
    var id : Int = 0
    var title : String? = ""
    var description : String? = ""
    var closest_to_sun  : Int = 0
    var largest_planet : Int = 0
    var distance_thesun_km  : String? = ""
    var distance_thesun_au  : String? = ""
    var diameter_km : String? = ""
    var categoryid : Int = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        title = parcel.readString()
        description = parcel.readString()
        closest_to_sun = parcel.readInt()
        largest_planet = parcel.readInt()
        distance_thesun_km = parcel.readString()
        distance_thesun_au = parcel.readString()
        diameter_km = parcel.readString()
        categoryid = parcel.readInt()
    }

    constructor(id : Int, title : String?, description : String?, closest_to_sun : Int,
                largest_planet : Int, distance_thesun_km : String, distance_thesun_au : String,
                diameter_km : String, categoryid : Int) : this(){
        this.id = id
        this.title = title
        this.description = description
        this.closest_to_sun = closest_to_sun
        this.largest_planet = largest_planet
        this.distance_thesun_au = distance_thesun_au
        this.distance_thesun_km = distance_thesun_km
        this.diameter_km = diameter_km
        this.categoryid = categoryid
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(closest_to_sun)
        parcel.writeInt(largest_planet)
        parcel.writeString(distance_thesun_km)
        parcel.writeString(distance_thesun_au)
        parcel.writeString(diameter_km)
        parcel.writeInt(categoryid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Planet> {
        override fun createFromParcel(parcel: Parcel): Planet {
            return Planet(parcel)
        }

        override fun newArray(size: Int): Array<Planet?> {
            return arrayOfNulls(size)
        }
    }

}