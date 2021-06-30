package com.magangonline.kiryucashier.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.model.TransaksiProduk
import kotlinx.android.synthetic.main.list_produk_konfirm.view.*
import java.util.ArrayList

class ProdukKonfirmAdapter(
        val item: ArrayList<TransaksiProduk>?,
        val context: Context,
        val onClick: OnClickItem) :
        RecyclerView.Adapter<ProdukKonfirmAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.list_produk_konfirm,
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
            view.tv_qty.text = produk.jumlah_pesanan
            view.card_item.setOnClickListener {
                clickItem.onClickItem(produk)
            }
        }

    }

    interface OnClickItem {
        fun onClickItem(data: TransaksiProduk)
    }
}