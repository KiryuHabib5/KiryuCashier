package com.magangonline.kiryucashier.feature.addproduk

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.magangonline.kiryucashier.model.Produk


class AddProdukPresenter(var view : AddProdukContract.View?) :AddProdukContract.Presenter {
    init {
        view?.presenter = this
    }

    override fun addProduk(produk: Produk) {
        view?.onProcess(true)
        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Produk")
        ref.push().setValue(produk).addOnSuccessListener {
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