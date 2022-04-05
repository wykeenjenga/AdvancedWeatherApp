package com.wyksofts.wyykweather.ui.helper

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns


class addCitytoFav(context: Context) {

    val dbHelper = FeedReaderDbHelper(context)

    val db = dbHelper.writableDatabase


    // Table contents are grouped together in an anonymous object.
    object CityEntry : BaseColumns {
        const val TABLE_NAME = "FavoriteCities"
        const val COLUMN_NAME_TITLE = "city"
        const val COLUMN_NAME_SUBTITLE = "cities"

        const val SQL_CREATE_ENTRIES = "CREATE TABLE ${CityEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${CityEntry.COLUMN_NAME_TITLE} TEXT," +
                "${CityEntry.COLUMN_NAME_SUBTITLE} TEXT)"

        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${CityEntry.TABLE_NAME}"
    }

    //add city to favorite
    fun addCity(city: String) {

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(CityEntry.COLUMN_NAME_TITLE, city)
        }

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(CityEntry.TABLE_NAME, null, values)
    }

    fun deleteCity(city: String){

        // Define 'where' part of query.
        val selection = "${CityEntry.COLUMN_NAME_TITLE} LIKE ?"

        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf("MyTitle")

        // Issue SQL statement.
        val deletedRows = db.delete(CityEntry.TABLE_NAME, selection, selectionArgs)
    }

    //delete city from favorite
    fun readCities() {

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(BaseColumns._ID, CityEntry.COLUMN_NAME_TITLE, CityEntry.COLUMN_NAME_SUBTITLE)

        // Filter results WHERE "title" = 'My Title'
        val selection = "${CityEntry.COLUMN_NAME_TITLE} = ?"
        val selectionArgs = arrayOf("My Title")

        // How you want the results sorted in the resulting Cursor
        val sortOrder = "${CityEntry.COLUMN_NAME_SUBTITLE} DESC"

        val cursor = db.query(
            CityEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            selectionArgs,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )

        val itemIds = mutableListOf<Long>()
        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                itemIds.add(itemId)
            }
        }
        cursor.close()

    }


}