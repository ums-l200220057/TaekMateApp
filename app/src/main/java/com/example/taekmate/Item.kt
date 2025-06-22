package com.example.taekmate

import androidx.annotation.DrawableRes

data class Item(
    val id: Int = 0,
    val name: String,
    val category: String,
    val purchasePrice: Int,
    val rentalPrice: Int,
    @DrawableRes val imageRes: Int,
)
