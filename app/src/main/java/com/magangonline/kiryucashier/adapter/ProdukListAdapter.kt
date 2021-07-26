package com.magangonline.kiryucashier.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.feature.Home.Dashboard.ui.transaksi.TransaksiFragment
import com.magangonline.kiryucashier.feature.Home.Dashboard.ui.transaksi.search.SearchFragment
import com.magangonline.kiryucashier.model.NewProduk
import com.magangonline.kiryucashier.model.TransaksiProduk
import kotlinx.android.synthetic.main.list_produk.view.*
import java.util.*
import kotlin.collections.ArrayList

class ProdukListAdapter(
        val item: ArrayList<NewProduk>?,
        val context: Context,
        val onClick: OnClickItem,
        val onResult: OnResult) :
        RecyclerView.Adapter<ProdukListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.list_produk,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item?.get(position)!!, onClick, onResult, position)
    }

    override fun getItemCount(): Int {
        return item?.size!!
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val view = view
        var transaksiProduk : MutableMap<String, Int> = mutableMapOf()
        fun bind(produk: NewProduk, clickItem: OnClickItem, result: OnResult, position: Int) {
            view.tv_nama.text = produk.nama_produk
            view.tv_jumlah.text = produk.itemClick
            view.tv_harga.text = (produk.harga_produk.toInt() * view.tv_jumlah.text.toString().toInt()).toString()
            transaksiProduk.clear()
            transaksiProduk[produk.kode_produk] = view.tv_harga.text.toString().toInt()
            result.result(transaksiProduk, TransaksiProduk(
                    nama_produk = view.tv_nama.text.toString(),
                    harga_produk = view.tv_harga.text.toString(),
                    jumlah_pesanan = view.tv_jumlah.text.toString()
            ), position, view.tv_jumlah.text.toString().toInt(),false)
            view.card_item.setOnClickListener {
                clickItem.onClickItem(produk)
            }
            view.ib_up.setOnClickListener {
                var jumlah: Int = view.tv_jumlah.text.toString().toInt() + 1
                view.tv_jumlah.text = jumlah.toString()
                view.tv_harga.text = (produk.harga_produk.toInt() * view.tv_jumlah.text.toString().toInt()).toString()
                transaksiProduk.clear()
                transaksiProduk[produk.kode_produk] = view.tv_harga.text.toString().toInt()
                result.result(transaksiProduk, TransaksiProduk(
                        nama_produk = view.tv_nama.text.toString(),
                        harga_produk = view.tv_harga.text.toString(),
                        jumlah_pesanan = view.tv_jumlah.text.toString()
                ), position, view.tv_jumlah.text.toString().toInt(),false)
            }
            view.ib_down.setOnClickListener {
                var jumlah: Int = view.tv_jumlah.text.toString().toInt() - 1
                if (jumlah < 1){
                    jumlah = 1
                }
                view.tv_jumlah.text = jumlah.toString()
                view.tv_harga.text = (produk.harga_produk.toInt() * view.tv_jumlah.text.toString().toInt()).toString()
                transaksiProduk.clear()
                transaksiProduk[produk.kode_produk] = view.tv_harga.text.toString().toInt()
                result.result(transaksiProduk, TransaksiProduk(
                        nama_produk = view.tv_nama.text.toString(),
                        harga_produk = view.tv_harga.text.toString(),
                        jumlah_pesanan = view.tv_jumlah.text.toString(),
                ),position, view.tv_jumlah.text.toString().toInt(), false)
            }
            view.btn_del.setOnClickListener {
                view.tv_harga.text = (produk.harga_produk.toInt() * view.tv_jumlah.text.toString().toInt()).toString()
                transaksiProduk.clear()
                transaksiProduk[produk.kode_produk] = view.tv_harga.text.toString().toInt()
                result.result(transaksiProduk, TransaksiProduk(
                        nama_produk = view.tv_nama.text.toString(),
                        harga_produk = view.tv_harga.text.toString(),
                        jumlah_pesanan = view.tv_jumlah.text.toString(),
                ),position, view.tv_jumlah.text.toString().toInt(),true)
            }

        }

    }


    interface OnClickItem {
        fun onClickItem(data: NewProduk)
    }

    interface OnResult {
        fun result(transaksiProduk: MutableMap<String, Int>,
                   produk:TransaksiProduk,
                   position: Int,
                   total:Int,
                   isDeleted: Boolean
        )
    }


}