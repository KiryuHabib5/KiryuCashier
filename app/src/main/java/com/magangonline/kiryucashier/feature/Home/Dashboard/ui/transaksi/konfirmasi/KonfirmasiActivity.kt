 package com.magangonline.kiryucashier.feature.Home.Dashboard.ui.transaksi.konfirmasi

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.adapter.ProdukKonfirmAdapter
import com.magangonline.kiryucashier.feature.Home.Dashboard.HomeActivity
import com.magangonline.kiryucashier.model.RiwayatTransaksi
import com.magangonline.kiryucashier.model.TransaksiProduk
import kotlinx.android.synthetic.main.activity_konfirmasi.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class KonfirmasiActivity : AppCompatActivity(), KonfirmasiContract.View {

    override lateinit var presenter: KonfirmasiContract.Presenter
//    private var produkResult: ArrayList<TransaksiProduk> = ArrayList()
    private var produkAdapter: ProdukKonfirmAdapter? = null
    private var listProduk : ArrayList<TransaksiProduk>? = ArrayList()
    private var loading: AlertDialog?=null
    init {
        KonfirmasiPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi)
        loading = showDialogLoading()
        btn_bayar.setOnClickListener {
            if (et_nominalBayar.text.toString().equals("0")){
                et_nominalBayar.setError("Anda Belum Input")
            }
            if (tv_nominalBiaya.text.toString().toInt() > et_nominalBayar.text.toString().toInt()){
                et_nominalBayar.setError("Buset kurang boss duitnya")
            }else{
                tv_nominalKembali.text = (et_nominalBayar.text.toString().toInt() - tv_nominalBiaya.text.toString().toInt()).toString()
            }
        }
        tv_nominalBiaya.text = intent.getStringExtra("biaya")
        listProduk = intent.getParcelableArrayListExtra("listProduk")
        addAdapter(listProduk!!)

        btn_end.setOnClickListener {

            presenter.save(RiwayatTransaksi(
                kode_transaksi = getCode().toString(),
                tanggal_transaksi = getDatetime().toString(),
                list_produk = listProduk as java.util.ArrayList<TransaksiProduk>,
                total_biaya = tv_nominalBiaya.text.toString(),
                input_bayar = et_nominalBayar.text.toString(),
                total_kembali = tv_nominalKembali.text.toString()
            ))

        }


    }

    private fun addAdapter(produk: ArrayList<TransaksiProduk>){

        listProduk = produk
        val onClickItem = object : ProdukKonfirmAdapter.OnClickItem {

            override fun onClickItem(data: TransaksiProduk) {

            }
        }
        produkAdapter = ProdukKonfirmAdapter(listProduk, this, onClickItem)
        rv_list.adapter = produkAdapter
        rv_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }


    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, HomeActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loading?.show()
        } else {
            loading?.dismiss()
        }
    }

    private fun showDialogLoading(): AlertDialog {
        val view = LayoutInflater.from(this).inflate(R.layout.item_layout, null, false)
        return AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCode(): String? {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")
        val formatted = current.format(formatter)
        return "T"+formatted
    }
    private fun getDatetime():String?{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = current.format(formatter)
        return formatted
    }
}