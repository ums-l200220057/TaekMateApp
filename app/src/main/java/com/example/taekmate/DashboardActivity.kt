package com.example.taekmate

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import android.widget.LinearLayout
import android.view.View
import android.widget.Button
import android.util.Log

data class Category(val name: String, val iconRes: Int)

class DashboardActivity : AppCompatActivity() {
    private lateinit var rvDashboard: RecyclerView
    private lateinit var rvCategory: RecyclerView
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var cartBar: LinearLayout
    private lateinit var tvCartItemCount: TextView
    private lateinit var tvCartTotal: TextView
    private lateinit var dbHelper: DBHelper // <-- kita buat satu instance global

    private fun addToCart(cartItem: CartItem) {
        val userPref = getSharedPreferences("user_session", MODE_PRIVATE)
        val username = userPref.getString("username", "defaultUser") ?: "defaultUser"
        val sharedPref = getSharedPreferences("cart_$username", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPref.getString("cart_items", "[]")
        val itemList = gson.fromJson(json, Array<CartItem>::class.java).toMutableList()
        itemList.add(cartItem)
        sharedPref.edit().putString("cart_items", gson.toJson(itemList)).apply()
        showCartBar()
    }

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
        setContentView(R.layout.activity_dashboard)

        // Inisialisasi DB hanya sekali
        dbHelper = DBHelper(this)
        dbHelper.writableDatabase // Paksa buat database kalau belum ada
        SeedData().seedItems(this) // Isi database kalau kosong

        // DEBUG: cek isi database
        Log.d("DEBUG_DB", "Jumlah item: ${dbHelper.getAllItems().size}")

        cartBar = findViewById(R.id.cartBar)
        tvCartItemCount = findViewById(R.id.tvCartItemCount)
        tvCartTotal = findViewById(R.id.tvCartTotal)

        cartBar.setOnClickListener {
            startActivity(Intent(this, OrderActivity::class.java))
        }

        showCartBar()

        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        val username = sharedPref.getString("username", "User")
        val greetingTextView = findViewById<TextView>(R.id.tvGreeting)
        greetingTextView.text = "Hi, $username"

        val logoutBtn = findViewById<Button>(R.id.btnLogout)
        logoutBtn.setOnClickListener {
            sharedPref.edit().clear().apply()
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        // Carousel
        val viewPager = findViewById<ViewPager2>(R.id.viewPagerCarousel)
        val realImages = listOf(
            R.drawable.logo_taekmate,
            R.drawable.logo_taekmate,
            R.drawable.logo_taekmate
        )
        val imageList = listOf(realImages.last()) + realImages + listOf(realImages.first())
        val carouselAdapter = CarouselAdapter(imageList)
        viewPager.adapter = carouselAdapter
        viewPager.setCurrentItem(1, false)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewPager.post {
                    if (position == imageList.size - 1) {
                        viewPager.setCurrentItem(1, false)
                    } else if (position == 0) {
                        viewPager.setCurrentItem(imageList.size - 2, false)
                    }
                }
            }
        })
        val handler = android.os.Handler()
        val runnable = object : Runnable {
            override fun run() {
                val nextItem = (viewPager.currentItem + 1) % imageList.size
                viewPager.setCurrentItem(nextItem, true)
                handler.postDelayed(this, 3000)
            }
        }
        handler.postDelayed(runnable, 3000)

        // Suggestion items
        val seragamList = dbHelper.getItemsByCategory("Seragam").shuffled().take(2)
        val protectorList = dbHelper.getItemsByCategory("Protector").shuffled().take(2)
        val alatLatihanList = dbHelper.getItemsByCategory("Alat Latihan").shuffled().take(2)
        val suggestionList = seragamList + protectorList + alatLatihanList

        // Kategori
        rvCategory = findViewById(R.id.rvCategory)
        rvCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val categoryList = listOf(
            Category("Seragam", R.drawable.logo_taekmate),
            Category("Protector", R.drawable.logo_taekmate),
            Category("Alat Latihan", R.drawable.logo_taekmate)
        )
        val categoryAdapter = CategoryAdapter(categoryList) { category ->
            when (category.name) {
                "Seragam" -> startActivity(Intent(this, SeragamActivity::class.java))
                "Protector" -> startActivity(Intent(this, ProtectorActivity::class.java))
                "Alat Latihan" -> startActivity(Intent(this, AlatLatihanActivity::class.java))
            }
        }
        rvCategory.adapter = categoryAdapter

        // Grid 2 kolom
        rvDashboard = findViewById(R.id.rvDashboard)
        rvDashboard.layoutManager = GridLayoutManager(this, 2)
        rvDashboard.adapter = DashboardAdapter(suggestionList) { item, action ->
            val message = when (action) {
                "sewa" -> {
                    addToCart(CartItem(item.name, 1, "sewa", item.rentalPrice, item.imageRes))
                    "Sewa: ${item.name} - Rp${item.rentalPrice}"
                }
                "beli" -> {
                    addToCart(CartItem(item.name, 1, "beli", item.purchasePrice, item.imageRes))
                    "Beli: ${item.name} - Rp${item.purchasePrice}"
                }
                else -> "Aksi tidak dikenal"
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Bottom Navigation
        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav.selectedItemId = R.id.nav_home
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_order -> {
                    startActivity(Intent(this, OrderActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}
