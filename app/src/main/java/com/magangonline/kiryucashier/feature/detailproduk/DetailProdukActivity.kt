package com.magangonline.kiryucashier.feature.detailproduk

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.feature.Home.Dashboard.HomeActivity
import com.magangonline.kiryucashier.model.Produk
import kotlinx.android.synthetic.main.activity_detail_produk.*

class DetailProdukActivity : AppCompatActivity(), DetailProdukContract.View {

    override lateinit var presenter: DetailProdukContract.Presenter
    private var loading: AlertDialog?=null

    init {
        DetailProdukPresenter(this)
    }

    private var produk : Produk? = null
    private var kode : String? = null
    private var idProduk : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produk)
        loading = showDialogLoading()
        kode = intent.getStringExtra("kode")
        presenter.getProduk(kode)
        btn_update.setOnClickListener {
            if(et_nama.text.toString().equals("")){
                et_nama.setError("Harus diisi!!")
            }else if(et_kode.text.toString().equals("")){
                et_kode.setError("Harus diisi!!")
            }else if(et_harga.text.toString().equals("")){
                et_harga.setError("Harus diisi!!")
            }else if(et_stok.text.toString().equals("")){
                et_stok.setError("harus diisi")
            }else {
                val produk = Produk(
                    et_kode.text.toString(),
                    et_nama.text.toString(),
                    et_harga.text.toString(),
                    et_stok.text.toString()
                )
                presenter.updateProduk(idProduk!!, produk)
            }
        }

        btn_delete.setOnClickListener {
            presenter.deleteProduk(idProduk!!)
        }
    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onGetData(produk: Produk, id: String?) {
        idProduk = id
        et_nama.setText(produk.nama_produk)
        et_kode.setText(produk.kode_produk)
        et_harga.setText(produk.harga_produk)
        et_stok.setText(produk.qty_produk)
    }

    override fun onSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        val goToHome = Intent(this, HomeActivity::class.java)
        goToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        goToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(goToHome)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()

    }

    override fun onSuccessDelete(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        val goToHome = Intent(this, HomeActivity::class.java)
        goToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        goToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(goToHome)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
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
}