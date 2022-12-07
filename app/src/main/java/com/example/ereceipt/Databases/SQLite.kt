package com.example.ereceipt.Databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class SQLite(context: Context): SQLiteOpenHelper(
    context, "user.db", null, 1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE companies" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nif TEXT, name TEXT, email TEXT, phoneNumber TEXT, address TEXT, city TEXT, country TEXT, postalCode TEXT)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS companies"
        db!!.execSQL(query)
        onCreate(db)
    }

    fun addCompany(nif: String, name: String, email: String, phoneNumber: String, address: String, city: String, country: String, postalCode: String){
        val data = contentValuesOf()
        data.put("nif", nif)
        data.put("name", name)
        data.put("email", email)
        data.put("phoneNumber", phoneNumber)
        data.put("address", address)
        data.put("city", city)
        data.put("country", country)
        data.put("postalCode", postalCode)

        val db = this.writableDatabase
        db.insert("companies", null, data)
        db.close()
    }
}