package com.magangonline.kiryucashier.feature.Home.Dashboard.ui.transaksi.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.adapter.ProdukAdapter
import com.magangonline.kiryucashier.model.Produk
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity(), SearchContract.View {

    override lateinit var presenter: SearchContract.Presenter
    private var produkAdapter: ProdukAdapter? = null
    private var listProduk : ArrayList<Produk>? = null
    private var searchListProduk : ArrayList<Produk>? = null
    private var loading: AlertDialog?=null
    private var by:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        SearchPresenter(this, this)
//        loading = showDialogLoading()
        presenter.getData()
        search.addTextChangedListener(onSearchChange("nama"))
    }
    private fun onSearchChange(by: String) : TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchListProduk = ArrayList()
                if (s?.isEmpty()!!) {
                    searchListProduk = listProduk
                } else {
                    searchListProduk = filterProduk(s.toString(), by)
                }
                val onClickItem = object : ProdukAdapter.OnClickItem {
                    override fun onClickItem(data: Produk) {
//                        startActivity(
//                            Intent(requireContext(), DetailProdukActivity::class.java)
//                                .putExtra("kode", data.kode_produk)
//                        )
                    }

                }
                produkAdapter = ProdukAdapter(searchListProduk, applicationContext, onClickItem)
                rv_produk.adapter = produkAdapter
                rv_produk.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

            }

            override fun afterTextChanged(s: Editable?) {

            }

        }
    }

    private fun filterProduk(search: String, by:String) : ArrayList<Produk> {
        val result: ArrayList<Produk> = ArrayList()
        if (listProduk?.size != null && listProduk?.size != 0){
            for (i in listProduk?.indices!!){
                if(by?.equals("nama")){
                    if (listProduk!!.get(i).nama_produk.toLowerCase(Locale.ROOT).contains(search.toLowerCase(
                                    Locale.ROOT))){
                        result.add(listProduk!![i])
                    }
                }else if(by?.equals("kode")){
                    if (listProduk!!.get(i).kode_produk.toLowerCase(Locale.ROOT).contains(search.toLowerCase(
                                    Locale.ROOT))){
                        result.add(listProduk!![i])
                    }
                }

            }
        }
        return result
    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onSuccess(produk: ArrayList<Produk>) {
        listProduk = produk
        val onClickItem = object : ProdukAdapter.OnClickItem {
            override fun onClickItem(data: Produk) {

            }

        }

        produkAdapter = ProdukAdapter(produk, this , onClickItem)
        rv_produk.adapter = produkAdapter
        rv_produk.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loading?.show()
        } else {
            loading?.dismiss()
        }
    }


//    private fun showDialogLoading(): AlertDialog {
//        val view = LayoutInflater.from(this).inflate(R.layout.item_layout, null, false)
//        return AlertDialog.Builder(this)
//            .setView(view)
//            .setCancelable(false)
//            .create()
//    }
}