package com.senlasy.planetz.model

import android.os.Parcel
import android.os.Parcelable

class SunFacts() : Parcelable  {
    var id : Int = 0
    var description: String? = ""
    var fav : Int = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        description = parcel.readString()
        fav = parcel.readInt()
    }


    constructor(id : Int, description: String?, fav : Int) : this(){
        this.id = id
        this.description = description
        this.fav = fav
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(description)
        parcel.writeInt(fav)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SunFacts> {
        override fun createFromParcel(parcel: Parcel): SunFacts {
            return SunFacts(parcel)
        }

        override fun newArray(size: Int): Array<SunFacts?> {
            return arrayOfNulls(size)
        }
    }


}