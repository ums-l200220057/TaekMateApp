package com.example.taekmate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson

class OrderActivity : AppCompatActivity() {

    private lateinit var rvCart: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        SeedData().seedItems(this)

        rvCart = findViewById(R.id.rvCart)
        rvCart.layoutManager = LinearLayoutManager(this)

        loadCartData() // ← panggil di onCreate juga, biar pertama kali muncul langsung keisi

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.selectedItemId = R.id.nav_order
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadCartData() // ← ini buat refresh data pas balik dari Rent/PaymentActivity
    }

    private fun loadCartData() {
        val username = getSharedPreferences("user_session", MODE_PRIVATE)
            .getString("username", "defaultUser") ?: "defaultUser"
        val sharedPref = getSharedPreferences("cart_$username", MODE_PRIVATE)
        val json = sharedPref.getString("cart_items", "[]")
        val itemList = Gson().fromJson(json, Array<CartItem>::class.java).toList()

        val adapter = CartAdapter(itemList) { item ->
            if (item.type == "sewa") {
                val intent = Intent(this, RentActivity::class.java)
                intent.putExtra("item_name", item.name)
                startActivity(intent)
            } else {
                val intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra("item_name", item.name)
                intent.putExtra("item_price", item.price)
                startActivity(intent)
            }
        }

        rvCart.adapter = adapter
    }
}