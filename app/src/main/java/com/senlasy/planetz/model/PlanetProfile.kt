package com.senlasy.planetz.model

import android.os.Parcel
import android.os.Parcelable

class PlanetProfile() : Parcelable {
    var id : Int = 0
    var mass : String? = ""
    var equatorial_diameter  : String? = ""
    var polar_diameter : String? = ""
    var equatorial_circumference : String? = ""
    var known_moons : String? = ""
    var notable_moons : String? = ""
    var orbit_distance : String? = ""
    var orbit_period : String? = ""
    var surface_temperature : String? = ""
    var first_record : String? = ""
    var recorded_by : String? = ""
    var planet_id : Int = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        mass = parcel.readString()
        equatorial_diameter = parcel.readString()
        polar_diameter = parcel.readString()
        equatorial_circumference = parcel.readString()
        known_moons = parcel.readString()
        notable_moons = parcel.readString()
        orbit_distance = parcel.readString()
        orbit_period = parcel.readString()
        surface_temperature = parcel.readString()
        first_record = parcel.readString()
        recorded_by = parcel.readString()
        planet_id = parcel.readInt()
    }

    constructor(id : Int, mass : String?, equatorial_diameter : String?, polar_diameter : String?,
                equatorial_circumference : String?, known_moons : String?, notable_moons : String?,
                orbit_distance : String?, orbit_period : String?, surface_temperature : String?,
                first_record : String?, recorded_by : String?, planet_id : Int) : this() {
        this.id = id
        this.mass = mass
        this.equatorial_diameter = equatorial_diameter
        this.polar_diameter = polar_diameter
        this.equatorial_circumference = equatorial_circumference
        this.known_moons = known_moons
        this.notable_moons = notable_moons
        this.orbit_distance  = orbit_distance
        this.orbit_period = orbit_period
        this.surface_temperature = surface_temperature
        this.first_record = first_record
        this.recorded_by = recorded_by
        this.planet_id = planet_id
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(mass)
        parcel.writeString(equatorial_diameter)
        parcel.writeString(polar_diameter)
        parcel.writeString(equatorial_circumference)
        parcel.writeString(known_moons)
        parcel.writeString(notable_moons)
        parcel.writeString(orbit_distance)
        parcel.writeString(orbit_period)
        parcel.writeString(surface_temperature)
        parcel.writeString(first_record)
        parcel.writeString(recorded_by)
        parcel.writeInt(planet_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlanetProfile> {
        override fun createFromParcel(parcel: Parcel): PlanetProfile {
            return PlanetProfile(parcel)
        }

        override fun newArray(size: Int): Array<PlanetProfile?> {
            return arrayOfNulls(size)
        }
    }

}