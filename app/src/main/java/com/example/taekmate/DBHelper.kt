package com.example.taekmate

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues


class DBHelper(context: Context) : SQLiteOpenHelper(context, "TaekMateDB", null, 39) {

    override fun onCreate(db: SQLiteDatabase) {
        // Table user
        db.execSQL(
            "CREATE TABLE users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT," +
                    "email TEXT," +
                    "password TEXT" +
                    ")"
        )

        // Table item
        db.execSQL(
            "CREATE TABLE items (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "category TEXT," +
                    "priceBuy INTEGER," +
                    "priceRent INTEGER," +
                    "image INTEGER" +
                    ")"
        )

        // Table cart
        db.execSQL(
            "CREATE TABLE cart (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "itemId INTEGER," +
                    "orderType TEXT," + // "buy" atau "rent"
                    "FOREIGN KEY(itemId) REFERENCES items(id)" +
                    ")"
        )
    }

    fun insertUser(username: String, email: String, password: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("username", username)
            put("email", email)
            put("password", password)
        }
        val result = db.insert("users", null, values)
        return result != -1L
    }

    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM users WHERE username = ? AND password = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        val result = cursor.count > 0
        cursor.close()
        return result
    }

    fun insertItem(
        name: String,
        category: String,
        priceBuy: Int,
        priceRent: Int,
        image: Int
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("category", category)
            put("priceBuy", priceBuy)
            put("priceRent", priceRent)
            put("image", image)
        }
        val result = db.insert("items", null, values)
        return result != -1L
    }

    fun getItemsByCategory(category: String): List<Item> {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM items WHERE category = ?",
            arrayOf(category)  // JANGAN lowercase
        )

        val items = mutableListOf<Item>()
        if (cursor.moveToFirst()) {
            do {
                items.add(
                    Item(
                        id = cursor.getInt(0),
                        name = cursor.getString(1),
                        category = cursor.getString(2),
                        purchasePrice = cursor.getInt(3),
                        rentalPrice = cursor.getInt(4),
                        imageRes = cursor.getInt(5)
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        return items
    }


    fun getAllItems(): List<Item> {
        val itemList = mutableListOf<Item>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM items", null)

        if (cursor.moveToFirst()) {
            do {
                itemList.add(
                    Item(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        category = cursor.getString(cursor.getColumnIndexOrThrow("category")),
                        purchasePrice = cursor.getInt(cursor.getColumnIndexOrThrow("priceBuy")),
                        rentalPrice = cursor.getInt(cursor.getColumnIndexOrThrow("priceRent")),
                        imageRes = cursor.getInt(cursor.getColumnIndexOrThrow("image")),
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        return itemList
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS items")
        db.execSQL("DROP TABLE IF EXISTS cart")
        onCreate(db)
    }
}
