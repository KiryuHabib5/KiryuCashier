package com.magangonline.kiryucashier.feature.addproduk

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.model.Produk
import kotlinx.android.synthetic.main.activity_add_produk.*
import kotlinx.android.synthetic.main.activity_add_produk.et_harga
import kotlinx.android.synthetic.main.activity_add_produk.et_kode
import kotlinx.android.synthetic.main.activity_add_produk.et_nama
import kotlinx.android.synthetic.main.activity_add_produk.et_stok
import kotlinx.android.synthetic.main.activity_detail_produk.*

class AddProdukActivity : AppCompatActivity(), AddProdukContract.View {

    override lateinit var presenter: AddProdukContract.Presenter
    private var loading: ProgressDialog? = null

    init {
        AddProdukPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_produk)
        loading = ProgressDialog(this)

        btn_add.setOnClickListener {
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
                presenter.addProduk(produk)
            }
        }

    }

    override fun onError(message: String) {
        loading?.dismiss()
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onSuccess(message: String) {
        loading?.dismiss()
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        et_kode.setText("")
        et_nama.setText("")
        et_stok.setText("")
        et_harga.setText("")
    }

    override fun onProcess(boolean: Boolean) {
        loading?.setMessage("Tunggu Sebentar")
        loading?.setCancelable(false)

        if(boolean){
            loading?.show()
        }else{
            loading?.dismiss()
        }
    }
}