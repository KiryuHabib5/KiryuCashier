package com.magangonline.kiryucashier.feature.Home.Dashboard.ui.transaksi

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.magangonline.kiryucashier.adapter.ProdukListAdapter
import com.magangonline.kiryucashier.model.NewProduk
import com.magangonline.kiryucashier.model.Produk
import kotlinx.android.synthetic.main.fragment_transaksi.*

class TransaksiPresenter(var context: Context, var view:TransaksiContract.View?): TransaksiContract.Presenter {
    init {
        view?.presenter = this
    }

//    private val allProduk : MutableMap<String, Produk> = mutableMapOf<String, Produk>()
    override fun getData(text: String) {
//        view?.onProcess(true)
//        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Produk")
//        ref.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val allProduk : ArrayList<NewProduk> = ArrayList()
//                for(produksnap in snapshot.children){
//                    val produk = produksnap.getValue(NewProduk::class.java)
//                    if(produk != null){
//                        if(produk!!.kode_produk.equals(text!!)){
//                            allProduk.add(produk)
//                        }
//                    }
//                }
//                view?.onSuccess(allProduk)
//                view?.onProcess(false)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                view?.onError(error.message!!)
//                view?.onProcess(false)
//            }
//
//        })
    }

    override fun getProd(text: String, jumlah: String) {
        view?.onProcess(true)
        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Produk")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val allProduk : ArrayList<NewProduk> = ArrayList()
                for(produksnap in snapshot.children){
                    val produk = produksnap.getValue(NewProduk::class.java)
                    if(produk != null){
                        if(produk!!.kode_produk.equals(text!!)){
                            produk.itemClick = jumlah
                            allProduk.add(produk)
                            view?.onSuccess(produk!!)
                            break
                        }
                    }
                }
//                view?.onSuccess(allProduk!!)
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