package com.example.taekmate

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class RentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent)

        val itemName = intent.getStringExtra("item_name")

        val tvInfo = findViewById<TextView>(R.id.tvRentInfo)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)

        tvInfo.text = "Silakan ambil $itemName di Sekretariat Taekwondo"

        btnConfirm.setOnClickListener {
            Toast.makeText(this, "Penyewaan berhasil!", Toast.LENGTH_SHORT).show()

            val userPref = getSharedPreferences("user_session", MODE_PRIVATE)
            val username = userPref.getString("username", "defaultUser") ?: "defaultUser"
            val sharedPref = getSharedPreferences("cart_$username", MODE_PRIVATE)
            val gson = Gson()

            val json = sharedPref.getString("cart_items", "[]")
            val cartList = gson.fromJson(json, Array<CartItem>::class.java).toMutableList()

            // Hapus item yang disewa
            val updatedCart = cartList.filterNot {
                it.name == itemName && it.type == "sewa"
            }

            sharedPref.edit().putString("cart_items", gson.toJson(updatedCart)).apply()

            finish()
        }


    }
}
