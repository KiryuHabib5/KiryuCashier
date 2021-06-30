package com.magangonline.kiryucashier.feature.Home.Dashboard.ui.produk

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.adapter.ProdukAdapter
import com.magangonline.kiryucashier.feature.addproduk.AddProdukActivity
import com.magangonline.kiryucashier.feature.detailproduk.DetailProdukActivity
import com.magangonline.kiryucashier.model.Produk
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*
import kotlin.collections.ArrayList

class ProdukFragment : Fragment(), ProdukContract.View {

    private lateinit var homeViewModel: HomeViewModel
    override lateinit var presenter: ProdukContract.Presenter
    private var produkAdapter: ProdukAdapter? = null
    private var listProduk : ArrayList<Produk>? = null
    private var searchListProduk : ArrayList<Produk>? = null
    private var loading: AlertDialog?=null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.title)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        root.fab.setOnClickListener{
            val gotoAdd = Intent(requireContext(), AddProdukActivity::class.java)
            startActivity(gotoAdd)
            activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ProdukPresenter(requireContext(), this)
        loading = showDialogLoading()
        presenter.getData()
        search.addTextChangedListener(onSearchChange())
    }

    private fun onSearchChange() : TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchListProduk = ArrayList()
                if (s?.isEmpty()!!) {
                    searchListProduk = listProduk
                } else {
                    searchListProduk = filterProduk(s.toString())
                }
                val onClickItem = object : ProdukAdapter.OnClickItem {
                    override fun onClickItem(data: Produk) {
                        startActivity(
                                Intent(requireContext(), DetailProdukActivity::class.java)
                                        .putExtra("kode", data.kode_produk)
                        )
                    }

                }
                produkAdapter = ProdukAdapter(searchListProduk, requireContext(), onClickItem)
                rv_produk.adapter = produkAdapter
                rv_produk.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            }

            override fun afterTextChanged(s: Editable?) {

            }

        }
    }

    private fun filterProduk(search: String) : ArrayList<Produk> {
        val result: ArrayList<Produk> = ArrayList()
        if (listProduk?.size != null && listProduk?.size != 0){
            for (i in listProduk?.indices!!){
                if (listProduk!!.get(i).nama_produk.toLowerCase(Locale.ROOT).contains(search.toLowerCase(
                                Locale.ROOT))){
                    result.add(listProduk!![i])
                }
            }
        }
        return result
    }

    override fun onError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onSuccess(produk: ArrayList<Produk>) {
        listProduk = produk
        listProduk!!.size
        val onClickItem = object : ProdukAdapter.OnClickItem {
            override fun onClickItem(data: Produk) {

                startActivity(
                        Intent(requireContext(), DetailProdukActivity::class.java)
                                .putExtra("kode", data.kode_produk)
                )
            }

        }

        produkAdapter = ProdukAdapter(produk, requireContext() , onClickItem)
        rv_produk.adapter = produkAdapter
        rv_produk.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loading?.show()
        } else {
            loading?.dismiss()
        }
    }

    private fun showDialogLoading(): AlertDialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.item_layout, null, false)
        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setCancelable(false)
            .create()
    }

}