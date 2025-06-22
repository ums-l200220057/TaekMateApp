package com.example.taekmate

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash) // tambahkan layout splash dulu

        Handler(Looper.getMainLooper()).postDelayed({
            val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
            val username = prefs.getString("username", null)
            val isFirstTime = prefs.getBoolean("isFirstTime", true)

            val nextIntent = when {
                isFirstTime -> {
                    // Pertama kali buka aplikasi
                    Intent(this, OnboardingActivity::class.java)
                }
                username != null -> {
                    // Sudah login
                    Intent(this, DashboardActivity::class.java)
                }
                else -> {
                    // Belum login
                    Intent(this, SignInActivity::class.java)
                }
            }

            startActivity(nextIntent)
            finish()
        }, 2000) // 2 detik splash
    }
}
