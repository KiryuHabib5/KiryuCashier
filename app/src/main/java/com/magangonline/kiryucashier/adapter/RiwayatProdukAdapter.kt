package com.magangonline.kiryucashier.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.model.TransaksiProduk
import kotlinx.android.synthetic.main.list_produk.view.*

class RiwayatProdukAdapter(
val item:ArrayList<TransaksiProduk>,
val context: Context,
val onClick: OnClickItem) : RecyclerView.Adapter<RiwayatProdukAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.list_produk_without_btn_del,
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
        fun bind(produk: TransaksiProduk, clickItem: OnClickItem) {
            view.tv_nama.text = produk.nama_produk
            view.tv_harga.text = produk.harga_produk
            view.tv_jumlah.text = produk.jumlah_pesanan
            view.card_item.setOnClickListener {
                clickItem.onClickItem(produk)
            }
        }
    }

    interface OnClickItem {
        fun onClickItem(data: TransaksiProduk)
    }
}