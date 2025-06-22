package com.example.taekmate

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class ProtectorActivity : AppCompatActivity() {

    private lateinit var rvProtector: RecyclerView
    private lateinit var cartBar: LinearLayout
    private lateinit var tvCartItemCount: TextView
    private lateinit var tvCartTotal: TextView

    private fun showCartBar() {
        val userPref = getSharedPreferences("user_session", MODE_PRIVATE)
        val username = userPref.getString("username", "defaultUser") ?: "defaultUser"
        val sharedPref = getSharedPreferences("cart_$username", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPref.getString("cart_items", "[]")
        val itemList = gson.fromJson(json, Array<CartItem>::class.java).toList()

        if (itemList.isNotEmpty()) {
            cartBar.visibility = View.VISIBLE
            tvCartItemCount.text = "${itemList.size} item"
            val total = itemList.sumOf { it.price * it.quantity }
            tvCartTotal.text = "Rp$total"
        } else {
            cartBar.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protector)

        cartBar = findViewById(R.id.cartBar)
        tvCartItemCount = findViewById(R.id.tvCartItemCount)
        tvCartTotal = findViewById(R.id.tvCartTotal)

        cartBar.setOnClickListener {
            startActivity(Intent(this, OrderActivity::class.java))
        }

        val btnBack = findViewById<android.widget.ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        rvProtector = findViewById(R.id.rvProtector)
        rvProtector.layoutManager = GridLayoutManager(this, 2)

        val dbHelper = DBHelper(this)
        val protectorItems = dbHelper.getItemsByCategory("Protector")

        rvProtector.adapter = DashboardAdapter(protectorItems) { item, action ->
            val cartItem = when (action) {
                "sewa" -> CartItem(item.name, 1, "sewa", item.rentalPrice, item.imageRes)
                "beli" -> CartItem(item.name, 1, "beli", item.purchasePrice, item.imageRes)
                else -> null
            }
            cartItem?.let {
                val userPref = getSharedPreferences("user_session", MODE_PRIVATE)
                val username = userPref.getString("username", "defaultUser") ?: "defaultUser"
                val sharedPref = getSharedPreferences("cart_$username", MODE_PRIVATE)
                val gson = Gson()
                val json = sharedPref.getString("cart_items", "[]")
                val itemList = gson.fromJson(json, Array<CartItem>::class.java).toMutableList()
                itemList.add(it)
                sharedPref.edit().putString("cart_items", gson.toJson(itemList)).apply()

                Toast.makeText(this, "${it.name} ditambahkan ke keranjang!", Toast.LENGTH_SHORT).show()
                showCartBar() // <--- Tambahkan ini di sini!
            }
            showCartBar()
        }
    }
    override fun onResume() {
        super.onResume()
        showCartBar()
    }
}
