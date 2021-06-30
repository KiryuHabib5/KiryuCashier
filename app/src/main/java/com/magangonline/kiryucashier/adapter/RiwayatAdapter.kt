package com.magangonline.kiryucashier.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.model.RiwayatTransaksi
import com.magangonline.kiryucashier.model.TransaksiProduk
import kotlinx.android.synthetic.main.item_produk.view.*
import kotlinx.android.synthetic.main.item_produk.view.card_item
import kotlinx.android.synthetic.main.riwayat_transaksi.view.*

class RiwayatAdapter(
    val item:ArrayList<RiwayatTransaksi>,
    val context: Context,
    val deleted:Delete,
    val onClick: OnClickItem) : RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.riwayat_transaksi,
                parent,
                false
            )
        )

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item?.get(position)!!, onClick, deleted)
    }

    override fun getItemCount(): Int {
        return item?.size!!
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val view = view
        fun bind(produk: RiwayatTransaksi, clickItem: OnClickItem, delete:Delete) {
            view.tv_valueCode.text = produk.kode_transaksi
            view.tv_valueBiaya.text = produk.total_biaya
            view.tv_valueBayar.text = produk.input_bayar
            view.tv_valueKembali.text = produk.total_kembali
            view.tv_datetime.text = produk.tanggal_transaksi
            view.card_item.setOnClickListener {
                clickItem.onClickItem(produk.list_produk)
            }
            view.btn_del.setOnClickListener {
                delete.onDelete(produk.kode_transaksi)
            }
        }
    }


    interface OnClickItem {
        fun onClickItem(data: ArrayList<TransaksiProduk>)
    }
    interface Delete{
        fun onDelete(kode: String)
    }
}