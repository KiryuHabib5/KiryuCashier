package com.magangonline.kiryucashier.feature.Home.Dashboard.ui.produk

import android.content.Context
import com.google.firebase.database.*
import com.magangonline.kiryucashier.model.Produk

class ProdukPresenter(var context: Context, var view: ProdukContract.View?) : ProdukContract.Presenter {

    init{
        view?.presenter = this
    }
    override fun getData() {
        view?.onProcess(true)
        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Produk")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val allProduk : ArrayList<Produk> = ArrayList()
                for(produksnap in snapshot.children){
                    val produk = produksnap.getValue(Produk::class.java)
                    if(produk != null){
                        allProduk.add(produk)
                    }
                }
                view?.onSuccess(allProduk)
                view?.onProcess(false)
            }

            override fun onCancelled(error: DatabaseError) {
                view?.onError(error.message!!)
                view?.onProcess(false)
            }

        })
    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun destroy() {
        view = null
    }
}