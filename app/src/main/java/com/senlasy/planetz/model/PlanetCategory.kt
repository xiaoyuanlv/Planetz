package com.senlasy.planetz.model

import android.os.Parcel
import android.os.Parcelable

class PlanetCategory() : Parcelable {
    var id : Int = 0
    var title : String? = ""
    var description : String? = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        title = parcel.readString()
        description = parcel.readString()
    }

    constructor(id: Int, title: String?, description: String?) : this(){
        this.id = id
        this.title = title
        this.description = description
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlanetCategory> {
        override fun createFromParcel(parcel: Parcel): PlanetCategory {
            return PlanetCategory(parcel)
        }

        override fun newArray(size: Int): Array<PlanetCategory?> {
            return arrayOfNulls(size)
        }
    }
}