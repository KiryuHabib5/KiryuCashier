package com.magangonline.kiryucashier.feature.detailproduk

import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.*
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.model.Produk

class DetailProdukPresenter (var view: DetailProdukContract.View?):  DetailProdukContract.Presenter{

    init {
        view?.presenter = this
    }

    override fun getProduk(id: String?) {
        view?.onProcess(true)
        val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Produk")
        ref.orderByChild("kode_produk").equalTo(id).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if ( snapshot.exists()){
                    for(data in snapshot.children){
                        val idProduk = data.key
                        val produk = data.getValue(Produk::class.java)
                        if(produk != null){
                            view?.onGetData(produk, idProduk)
                            view?.onProcess(false)
                        }else{
                            view?.onError("Data Tidak Ditemukan")
                            view?.onProcess(false)
                        }
                    }
                }else{
                    view?.onError("Data Tidak Ditemukan")
                    view?.onProcess(false)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                view?.onError(error.message)
                view?.onProcess(false)
            }

        })
    }

    override fun updateProduk(idProduk: String, produk: Produk) {
        view?.onProcess(true)
        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Produk")
        ref.child(idProduk).setValue(produk).addOnSuccessListener {
            view?.onSuccess("Success")
            view?.onProcess(false)
        }.addOnFailureListener {
            view?.onError(it.message!!)
            view?.onProcess(false)
        }
    }

    override fun deleteProduk(idProduk: String) {
        view?.onProcess(true)
        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Produk")
        ref.child(idProduk).removeValue().addOnSuccessListener {
            view?.onSuccessDelete("Success")
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