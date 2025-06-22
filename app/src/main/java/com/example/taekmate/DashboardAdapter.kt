package com.example.taekmate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView

class DashboardAdapter(
    private val list: List<Item>,
    private val listener: (Item, String) -> Unit
) : RecyclerView.Adapter<DashboardAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        private val img: ImageView = v.findViewById(R.id.itemImage)
        private val name: TextView = v.findViewById(R.id.itemName)
        private val rentalPrice: TextView = v.findViewById(R.id.itemRentalPrice)
        private val purchasePrice: TextView = v.findViewById(R.id.itemPurchasePrice)
        private val btnRent: Button = v.findViewById(R.id.btnRent)
        private val btnBuy: Button = v.findViewById(R.id.btnBuy)

        fun bind(item: Item) {
            img.setImageResource(item.imageRes)
            name.text = item.name
            rentalPrice.text = "Sewa: Rp${item.rentalPrice}"
            purchasePrice.text = "Beli: Rp${item.purchasePrice}"

            btnRent.setOnClickListener { listener(item, "sewa") }
            btnBuy.setOnClickListener { listener(item, "beli") }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dashboard, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size
}
