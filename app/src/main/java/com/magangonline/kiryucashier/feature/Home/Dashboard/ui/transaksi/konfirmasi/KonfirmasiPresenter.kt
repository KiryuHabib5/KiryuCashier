package com.magangonline.kiryucashier.feature.Home.Dashboard.ui.transaksi.konfirmasi

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.magangonline.kiryucashier.model.RiwayatTransaksi
import com.magangonline.kiryucashier.model.TransaksiProduk

class KonfirmasiPresenter(val context: Context, var view: KonfirmasiContract.View?): KonfirmasiContract.Presenter {

    init {
        view?.presenter = this
    }

    override fun save(item: RiwayatTransaksi) {
        view?.onProcess(true)
        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Riwayat")
        ref.push().setValue(item).addOnSuccessListener {
            view?.onSuccess("Success")
            view?.onProcess(false)
        }.addOnFailureListener {
            view?.onError(it.message!!)
            view?.onProcess(false)
        }


    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun destroy() {
        view = null
    }
}