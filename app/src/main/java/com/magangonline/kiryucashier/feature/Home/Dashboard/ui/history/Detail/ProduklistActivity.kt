package com.magangonline.kiryucashier.feature.Home.Dashboard.ui.history.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.adapter.RiwayatAdapter
import com.magangonline.kiryucashier.adapter.RiwayatProdukAdapter
import com.magangonline.kiryucashier.model.TransaksiProduk
import kotlinx.android.synthetic.main.activity_produklist.*

class ProduklistActivity : AppCompatActivity() {
    private var adapter: RiwayatProdukAdapter? = null
    private var loading: AlertDialog?=null
    private var produk: ArrayList<TransaksiProduk> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produklist)
        produk = intent.getParcelableArrayListExtra("kode")!!
        val onClickItem = object :RiwayatProdukAdapter.OnClickItem{
            override fun onClickItem(data: TransaksiProduk) {

            }
        }

        adapter = RiwayatProdukAdapter(produk, this , onClickItem)
        rv_produk.adapter = adapter
        rv_produk.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}