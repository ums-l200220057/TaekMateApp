package com.example.taekmate

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        dbHelper = DBHelper(this)

        val usernameInput = findViewById<EditText>(R.id.usernameSignup)
        val emailInput = findViewById<EditText>(R.id.emailSignup)
        val passwordInput = findViewById<EditText>(R.id.passwordSignup)
        val btnSignup = findViewById<Button>(R.id.btnSignup)

        btnSignup.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (dbHelper.checkUser(username, password)) {
                Toast.makeText(this, "Username sudah digunakan", Toast.LENGTH_SHORT).show()
            } else {
                val success = dbHelper.insertUser(username, email, password)
                if (success) {
                    Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, SignInActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Registrasi gagal", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
