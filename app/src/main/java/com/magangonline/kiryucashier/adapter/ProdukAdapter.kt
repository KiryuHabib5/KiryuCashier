package com.magangonline.kiryucashier.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.model.Produk
import kotlinx.android.synthetic.main.item_produk.view.*
import java.util.*

class ProdukAdapter(
        val item: ArrayList<Produk>?,
        val context: Context,
        val onClick: OnClickItem) :
        RecyclerView.Adapter<ProdukAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_produk,
                        parent,
                        false
                )
        )

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item?.get(position)!!, onClick)
    }

    override fun getItemCount(): Int {
        return item?.size!!
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bind(produk: Produk, clickItem: OnClickItem) {
            view.tv_kode.text = produk.kode_produk
            view.tv_nama.text = produk.nama_produk
            view.tv_harga.text = produk.harga_produk
            view.tv_qty.text = produk.qty_produk
            view.card_item.setOnClickListener {
                clickItem.onClickItem(produk)
            }
        }

    }

    interface OnClickItem {
        fun onClickItem(data: Produk)
    }

}