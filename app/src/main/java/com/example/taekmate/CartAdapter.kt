package com.example.taekmate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val itemList: List<CartItem>,
    private val onActionClick: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvType: TextView = view.findViewById(R.id.tvType)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val ivImage: ImageView = view.findViewById(R.id.ivImage)
        val btnAction: Button = view.findViewById(R.id.btnAction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = itemList[position]
        holder.tvName.text = item.name
        holder.tvType.text = item.type.capitalize()
        holder.tvPrice.text = "Rp${item.price}"
        holder.ivImage.setImageResource(item.imageRes)
        holder.btnAction.text = if (item.type == "sewa") "Sewa" else "Beli"

        holder.btnAction.setOnClickListener {
            onActionClick(item)
        }
    }

    override fun getItemCount(): Int = itemList.size
}
