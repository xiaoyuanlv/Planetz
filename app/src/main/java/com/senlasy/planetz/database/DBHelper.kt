package com.senlasy.planetz.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.senlasy.planetz.model.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DBHelper (private val context: Context) {

    companion object {
        private val DB_NAME = "theplanets.db"
        private val TBL_MOONS = "tbl_moons"
        private val TBL_PLANET = "tbl_planet"
        private val TBL_PLANET_CATEGORY = "tbl_planet_category"
        private val TBL_PLANET_FACTS = "tbl_planet_facts"
        private val TBL_PLANET_PROFILE = "tbl_planet_profile"
        private val TBL_SUN_FACTS = "tbl_sun_facts"
        private val TBL_THEMOON = "tbl_themoon"
        private val TBL_THESUN = "tbl_thesun"
    }

    fun openDatabase(): SQLiteDatabase {
        val dbFile = context.getDatabasePath(DB_NAME)

        if (!dbFile.exists()) {
            try {
                val checkDB = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,null)

                checkDB?.close()
                copyDatabase(dbFile)
            } catch (e: IOException) {
                throw RuntimeException("Error creating source database", e)
            }

        }
        return SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    @SuppressLint("WrongConstant")
    private fun copyDatabase(dbFile: File) {
        val `is` = context.assets.open(DB_NAME)
        val os = FileOutputStream(dbFile)

        val buffer = ByteArray(1024)
        while (`is`.read(buffer) > 0) {
            os.write(buffer)
            Log.d("#DB", "writing>>")
        }

        os.flush()
        os.close()
        `is`.close()
        Log.d("#DB", "completed..")
    }


    fun getAllPlanetCategory(): List<PlanetCategory> {
        val lstCategory = ArrayList<PlanetCategory>()

        // Select All Query
        val selectQuery = "SELECT  * FROM " + TBL_PLANET_CATEGORY + " ORDER BY " +
                " id ASC"

        val db = this.openDatabase()
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val category = PlanetCategory(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("description")))

                lstCategory.add(category)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lstCategory
    }

    fun getAllPlanet(sortBy : String): List<Planet> {
        val lstPlanet = ArrayList<Planet>()

        // Select All Query
        var selectQuery = "SELECT  * FROM " + TBL_PLANET

        if(sortBy.trim() == "diameter"){
            selectQuery += " ORDER BY largest_planet ASC"
        } else {
            selectQuery += " ORDER BY closest_to_sun ASC"
        }

        val db = this.openDatabase()
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val planetx = Planet(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getInt(cursor.getColumnIndex("closest_to_sun")),
                    cursor.getInt(cursor.getColumnIndex("largest_planet")),
                    cursor.getString(cursor.getColumnIndex("distance_thesun_km")),
                    cursor.getString(cursor.getColumnIndex("distance_thesun_au")),
                    cursor.getString(cursor.getColumnIndex("diameter_km")),
                    cursor.getInt(cursor.getColumnIndex("categoryid")))

                lstPlanet.add(planetx)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lstPlanet
    }

    fun getPlanetByCategory(categoryid : Int, sortBy : String): List<Planet> {
        val lstPlanet = ArrayList<Planet>()

        // Select All Query
        var selectQuery = "SELECT  * FROM " + TBL_PLANET +
             " where categoryid=" + categoryid

        if(sortBy.trim() == "diameter"){
            selectQuery += " ORDER BY largest_planet ASC"
        } else {
            selectQuery += " ORDER BY closest_to_sun ASC"
        }

        val db = this.openDatabase()
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val planetx = Planet(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getInt(cursor.getColumnIndex("closest_to_sun")),
                    cursor.getInt(cursor.getColumnIndex("largest_planet")),
                    cursor.getString(cursor.getColumnIndex("distance_thesun_km")),
                    cursor.getString(cursor.getColumnIndex("distance_thesun_au")),
                    cursor.getString(cursor.getColumnIndex("diameter_km")),
                    cursor.getInt(cursor.getColumnIndex("categoryid")))

                lstPlanet.add(planetx)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lstPlanet
    }

    fun getPlanet(id : Int): Planet? {
        var planet : Planet? = null

        // Select All Query
        var selectQuery = "SELECT  * FROM " + TBL_PLANET +
                " where id=" + id

        val db = this.openDatabase()
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val planetx = Planet(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getInt(cursor.getColumnIndex("closest_to_sun")),
                    cursor.getInt(cursor.getColumnIndex("largest_planet")),
                    cursor.getString(cursor.getColumnIndex("distance_thesun_km")),
                    cursor.getString(cursor.getColumnIndex("distance_thesun_au")),
                    cursor.getString(cursor.getColumnIndex("diameter_km")),
                    cursor.getInt(cursor.getColumnIndex("categoryid")))

                planet = planetx
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return planet
    }

    fun getPlanetFact(planet_id : Int) : List<PlanetFacts> {
        val lstFacts = ArrayList<PlanetFacts>()

        // Select All Query
        var selectQuery = "SELECT  * FROM " + TBL_PLANET_FACTS

        if(planet_id > 0){
            selectQuery += " where planet_id=" + planet_id
        }

        selectQuery += " ORDER BY " +
                " id ASC"

        val db = this.openDatabase()
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val fact = PlanetFacts(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getInt(cursor.getColumnIndex("planet_id")),
                    cursor.getInt(cursor.getColumnIndex("fav")))

                lstFacts.add(fact)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lstFacts
    }

    fun getPlanetProfile(planet_id: Int) : PlanetProfile? {
        var planet_profile : PlanetProfile? = null

        // Select All Query
        val selectQuery = "SELECT  * FROM " + TBL_PLANET_PROFILE +
                " where planet_id=" + planet_id

        val db = this.openDatabase()
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val profile = PlanetProfile(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("mass")),
                    cursor.getString(cursor.getColumnIndex("equatorial_diameter")),
                    cursor.getString(cursor.getColumnIndex("polar_diameter")),
                    cursor.getString(cursor.getColumnIndex("equatorial_circumference")),
                    cursor.getString(cursor.getColumnIndex("known_moons")),
                    cursor.getString(cursor.getColumnIndex("notable_moons")),
                    cursor.getString(cursor.getColumnIndex("orbit_distance")),
                    cursor.getString(cursor.getColumnIndex("orbit_period")),
                    cursor.getString(cursor.getColumnIndex("surface_temperature")),
                    cursor.getString(cursor.getColumnIndex("first_record")),
                    cursor.getString(cursor.getColumnIndex("recorded_by")),
                    cursor.getInt(cursor.getColumnIndex("planet_id")))

                planet_profile = profile
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return planet_profile
    }

    fun getSunFacts() : List<SunFacts> {
        val lstFacts = ArrayList<SunFacts>()

        // Select All Query
        val selectQuery = "SELECT  * FROM " + TBL_SUN_FACTS + " order by id asc"

        val db = this.openDatabase()
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val fact = SunFacts(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getInt(cursor.getColumnIndex("fav")))

                lstFacts.add(fact)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lstFacts
    }

    fun getTheMoon() : TheMoon? {
        var themoon : TheMoon? = null

        // Select All Query
        val selectQuery = "SELECT  * FROM " + TBL_THEMOON

        val db = this.openDatabase()
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                 themoon = TheMoon(cursor.getString(cursor.getColumnIndex("the_age")),
                    cursor.getString(cursor.getColumnIndex("the_mass")),
                    cursor.getString(cursor.getColumnIndex("the_diameter")),
                    cursor.getString(cursor.getColumnIndex("the_circumference_at_equator")),
                    cursor.getString(cursor.getColumnIndex("surface_temperature")))

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return themoon
    }

    fun getTheSun() : TheSun? {
        var thesun : TheSun? = null

        // Select All Query
        val selectQuery = "SELECT  * FROM " + TBL_THESUN + " order by id asc"

        val db = this.openDatabase()
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                thesun = TheSun(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("the_age")),
                    cursor.getString(cursor.getColumnIndex("the_mass")),
                    cursor.getString(cursor.getColumnIndex("the_type")),
                    cursor.getString(cursor.getColumnIndex("the_diameter")),
                    cursor.getString(cursor.getColumnIndex("the_circumference_at_equator")),
                    cursor.getString(cursor.getColumnIndex("surface_temperature")))

            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return thesun
    }

    fun getMoonsList(planet_id: Int) : List<Moons> {
        val lstMoons = ArrayList<Moons>()

        // Select All Query
        val selectQuery = "SELECT  * FROM " + TBL_MOONS +
                " WHERE planet_id=" + planet_id
                " order by id asc"

        val db = this.openDatabase()
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val fact = Moons(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("discovered")),
                    cursor.getString(cursor.getColumnIndex("distance")),
                    cursor.getString(cursor.getColumnIndex("diameter")) ,
                    cursor.getString(cursor.getColumnIndex("orbital_period")),
                    cursor.getInt(cursor.getColumnIndex("planet_id")))

                lstMoons.add(fact)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lstMoons
    }


    fun getAllMoonsList() : List<Moons> {
        val lstMoons = ArrayList<Moons>()

        // Select All Query
        val selectQuery = "SELECT  * FROM " + TBL_MOONS + " order by id asc"

        val db = this.openDatabase()
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val fact = Moons(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("discovered")),
                    cursor.getString(cursor.getColumnIndex("distance")),
                    cursor.getString(cursor.getColumnIndex("diameter")) ,
                    cursor.getString(cursor.getColumnIndex("orbital_period")),
                    cursor.getInt(cursor.getColumnIndex("planet_id")))

                lstMoons.add(fact)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lstMoons
    }

    fun updateFavPlanet(id: Int, fav : Int) : Int {

        val db = this.openDatabase()
        var contentVal = ContentValues()
        contentVal.put("fav", fav)

        val res = db.update(TBL_PLANET_FACTS, contentVal, "id=?", Array(1){id.toString()})

        db.close()
        return res
    }

    fun getFavPlanetFact(planet_id : Int) : List<PlanetFacts> {
        val lstFacts = ArrayList<PlanetFacts>()

        // Select All Query
        var selectQuery = "SELECT  * FROM " + TBL_PLANET_FACTS +
                " where fav=1 "

        if(planet_id > 0){
            selectQuery += " and planet_id=" + planet_id
        }

        val db = this.openDatabase()
        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val fact = PlanetFacts(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getInt(cursor.getColumnIndex("planet_id")),
                    cursor.getInt(cursor.getColumnIndex("fav")))

                lstFacts.add(fact)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lstFacts
    }

}