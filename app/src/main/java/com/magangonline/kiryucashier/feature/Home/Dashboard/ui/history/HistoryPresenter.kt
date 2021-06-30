package com.magangonline.kiryucashier.feature.Home.Dashboard.ui.history

import android.content.Context
import com.google.firebase.database.*
import com.magangonline.kiryucashier.model.RiwayatTransaksi


class HistoryPresenter(val context: Context, var view: HistoryContract.View?): HistoryContract.Presenter {

    init {
        view?.presenter = this
    }

    override fun getData() {
        view?.onProcess(true)
        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Riwayat")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val allProduk: ArrayList<RiwayatTransaksi> = ArrayList()
                for (produksnap in snapshot.children) {
                    val produk = produksnap.getValue(RiwayatTransaksi::class.java)
                    if (produk != null) {
                        allProduk.add(produk)
                    }
                }
                view?.onSuccess(allProduk)
                view?.onProcess(false)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun deleteProduk(id: String) {
        view?.onProcess(true)
        val ref : DatabaseReference = FirebaseDatabase.getInstance().reference
//        ref.child(id).removeValue().addOnSuccessListener {
//            view?.onSuccessDelete("Success")
//            view?.onProcess(false)
//        }.addOnFailureListener {
//            view?.onError(it.message!!)
//            view?.onProcess(false)
//        }
        val query:Query = ref.child("Riwayat").orderByChild("kode_transaksi").equalTo(id)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (appleSnapshot in snapshot.children) {
                    appleSnapshot.ref.removeValue()
                }
                view?.onSuccessDelete("Success")
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