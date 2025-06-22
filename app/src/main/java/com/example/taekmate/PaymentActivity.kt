package com.example.taekmate

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val itemName = intent.getStringExtra("item_name")
        val itemPrice = intent.getIntExtra("item_price", 0)

        val tvItem = findViewById<TextView>(R.id.tvPaymentItem)
        val tvTotal = findViewById<TextView>(R.id.tvTotalPrice)
        val btnPay = findViewById<Button>(R.id.btnPay)

        tvItem.text = "Item: $itemName"
        tvTotal.text = "Total: Rp$itemPrice"

        btnPay.setOnClickListener {
            Toast.makeText(this, "Pembayaran berhasil!", Toast.LENGTH_SHORT).show()

            val userPref = getSharedPreferences("user_session", MODE_PRIVATE)
            val username = userPref.getString("username", "defaultUser") ?: "defaultUser"
            val sharedPref = getSharedPreferences("cart_$username", MODE_PRIVATE)
            val gson = Gson()

            val json = sharedPref.getString("cart_items", "[]")
            val cartList = gson.fromJson(json, Array<CartItem>::class.java).toMutableList()

            // Hapus item yang dibayar (berdasarkan nama dan tipe)
            val updatedCart = cartList.filterNot {
                it.name == itemName && it.type == "beli"
            }

            // Simpan ulang cart yang udah difilter
            sharedPref.edit().putString("cart_items", gson.toJson(updatedCart)).apply()

            finish() // balik ke OrderActivity, dan OrderActivity akan reload isi cart
        }
    }
}
