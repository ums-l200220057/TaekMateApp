package com.example.taekmate

data class CartItem(
    val name: String,
    val quantity: Int,
    val type: String, // "sewa" atau "beli"
    val price: Int,
    val imageRes: Int = R.drawable.logo_taekmate // default image biar ngga error
)
