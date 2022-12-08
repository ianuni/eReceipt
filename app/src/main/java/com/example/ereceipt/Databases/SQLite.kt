package com.example.ereceipt.Databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf
import com.example.ereceipt.Model.Company

class SQLite(context: Context): SQLiteOpenHelper(
    context, "user.db", null, 1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        var query = "CREATE TABLE companies" +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nif TEXT, name TEXT, phoneNumber TEXT, address TEXT, postalCode TEXT, city TEXT, country TEXT, email TEXT)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS companies"
        db!!.execSQL(query)
        onCreate(db)
    }

    fun addCompany(company: Company): Boolean{
        var res: Boolean = false
        if (getCompany(company.nif) == null) {
            val data = contentValuesOf()
            data.put("nif", company.nif)
            data.put("name", company.name)
            data.put("phoneNumber", company.phoneNumber)
            data.put("address", company.address)
            data.put("postalCode",company.postalCode)
            data.put("city",company.city)
            data.put("country", company.country)
            data.put("email", company.email)

            val db = this.writableDatabase
            Log.e("Insercion", db.insert("companies", null, data).toString())
            db.close()

            res = true
        }
        return res
    }

    fun getCompany(nif: String): Company? {
        return try {
            var company: Company? = null
            val db = this.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM companies WHERE nif=${nif}", null)

            if (cursor.moveToFirst()){ //Si existe algún registro en la base de datos
                company = Company(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8)
                )
            }
            company
        } catch (e: Exception){
            Log.e("eew", "exception")
            null
        }
    }

    fun getCompanies(): List<Company>? {
        return try{
            var list: MutableList<Company> = mutableListOf()
            val db = this.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM companies", null)
            if (cursor.moveToFirst()){
                do {
                    list.add(Company(
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getString(8)))
                } while (cursor.moveToNext())
            }
            list
        } catch (e: Exception) {
            null
        }
    }

    fun deleteCompany(){

    }

    fun updateCompany(){

    }

}