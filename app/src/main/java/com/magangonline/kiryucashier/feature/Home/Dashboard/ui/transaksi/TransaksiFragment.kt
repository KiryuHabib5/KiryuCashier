package com.magangonline.kiryucashier.feature.Home.Dashboard.ui.transaksi

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.adapter.ProdukListAdapter
import com.magangonline.kiryucashier.feature.Home.Dashboard.ui.transaksi.konfirmasi.KonfirmasiActivity
import com.magangonline.kiryucashier.feature.Home.Dashboard.ui.transaksi.search.SearchFragment
import com.magangonline.kiryucashier.model.NewProduk
import com.magangonline.kiryucashier.model.Produk
import com.magangonline.kiryucashier.model.TransaksiProduk
import kotlinx.android.synthetic.main.fragment_transaksi.*
import kotlinx.android.synthetic.main.fragment_transaksi.view.*
import kotlinx.android.synthetic.main.list_produk.view.*


class TransaksiFragment : Fragment(),TransaksiContract.View {

    private lateinit var galleryViewModel: GalleryViewModel
    override lateinit var presenter: TransaksiContract.Presenter
    private var loading: AlertDialog?=null

    private var produkAdapter: ProdukListAdapter? = null
    private var listProduk : ArrayList<NewProduk>? = ArrayList()
    private var produkTransaksi : MutableMap<String, Int> = mutableMapOf()
    private var jumlah:Int = 1
//    private var ttlHarga:String = ""
//    private var code:String = ""
    private val kdProduk = mutableMapOf<String, String>()
    private var allProduk: ArrayList<NewProduk>? = ArrayList()
    private var produkResult:  MutableMap<String, TransaksiProduk> = mutableMapOf()
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
                ViewModelProvider(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_transaksi, container, false)
//        val textView: TextView = root.findViewById(R.id.text_gallery)
//        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        root.kode.setOnClickListener {
            val metrics = resources.displayMetrics
            val width = metrics.widthPixels
            val height = metrics.heightPixels
            val fragment = SearchFragment()
            fragment.setOnOptionDialogListener(object : SearchFragment.OnOptionDialogListener {
                override fun onOptionChoosen(text: String?) {
                    if (text !in kdProduk.keys){
                        kdProduk[text!!] = 1.toString()
                        jumlah = kdProduk[text!!]!!.toInt()
                    }else{
                        jumlah = kdProduk[text!!]!!.toInt() + 1
                        kdProduk[text!!] = jumlah.toString()
                    }
                    presenter.getProd(text, jumlah.toString())
                }
            })
            fragment.dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            val bundle:Bundle = Bundle()
            bundle.putString("by", "kode")
            fragment.arguments = bundle
            fragment.show(requireFragmentManager()!!, "a")
        }

        root.nama.setOnClickListener {
            val metrics = resources.displayMetrics
            val width = metrics.widthPixels
            val height = metrics.heightPixels
            val fragment = SearchFragment()
            fragment.setOnOptionDialogListener(object : SearchFragment.OnOptionDialogListener {
                override fun onOptionChoosen(text: String?) {
                    if (text !in kdProduk.keys){
                        kdProduk[text!!] = 1.toString()
                        jumlah = kdProduk[text!!]!!.toInt()
                    }else{
                        jumlah = kdProduk[text!!]!!.toInt() + 1
                        kdProduk[text!!] = jumlah.toString()
                    }
                    presenter.getProd(text, jumlah.toString())
                }
            })
            fragment.dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            val bundle:Bundle = Bundle()
            bundle.putString("by", "nama")
            fragment.arguments = bundle
            fragment.show(requireFragmentManager()!!, "a")
        }

        root.btn_continue_transaksi.setOnClickListener {
            val goToKonfirmasiAct = Intent(requireContext(), KonfirmasiActivity::class.java)
            var list:ArrayList<TransaksiProduk> = ArrayList(produkResult.values)
            goToKonfirmasiAct.putExtra("listProduk", list)
            goToKonfirmasiAct.putExtra("biaya", root.tv_ttlHarga.text.toString())
            startActivity(goToKonfirmasiAct)
            requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

            return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TransaksiPresenter(requireContext(), this)
    }


    override fun onError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onSuccess(produk: NewProduk) {

        if (allProduk!!.isNotEmpty()){
            var tempKD:ArrayList<String>? = ArrayList()

            for (prod in allProduk!!){
                tempKD!!.add(prod.kode_produk)
                for (kdProd in kdProduk.keys){
                    if (prod.kode_produk == kdProd){
                        prod.itemClick = kdProduk[kdProd]!!
                    }
                }
            }
            if (produk.kode_produk in tempKD!!){
                for (prod in allProduk!!){
                    if (produk.kode_produk == prod.kode_produk){
                        var index = allProduk!!.indexOf(prod)
                        allProduk!![index] = produk
                    }
                }
            }else{
                allProduk!!.add(produk)
            }
        }else{
            allProduk?.add(produk)
        }
        addAdapter(allProduk!!)
    }

    private fun addAdapter(produk: ArrayList<NewProduk>){
        val onClickItem = object : ProdukListAdapter.OnClickItem {
            override fun onClickItem(data: NewProduk) {
//                Toast.makeText(requireContext(), data.kode_produk, Toast.LENGTH_LONG).show()
            }
        }
        val onResult = object : ProdukListAdapter.OnResult {
            override fun result(transaksiProduk: MutableMap<String, Int>, produkT: TransaksiProduk, position: Int, total: Int, isDeleted: Boolean) {
//                Toast.makeText(requireContext(), harga, Toast.LENGTH_LONG).show

                try {
                    var kd = listProduk!![position].kode_produk
                    kdProduk[kd] = total.toString()
                    for (id in transaksiProduk.keys){
                        produkTransaksi[id] = transaksiProduk[id]!!
                        produkResult[id] = produkT
                    }



                    if (isDeleted){
//                    Toast.makeText(requireContext(), "Delete", Toast.LENGTH_LONG).show()
                        produkTransaksi.remove(kd)
                        kdProduk!!.remove(kd)
                        listProduk!!.clear()
                        listProduk!!.addAll(produk)
                        listProduk!!.removeAt(position)
                        allProduk!!.removeAt(position)
                        produkAdapter!!.notifyItemRemoved(position)
                    }
                }catch (e:Exception){

                }




                var temp:Int = 0
                if (produkTransaksi.isNotEmpty()){
                    for (value in produkTransaksi.values){
                        temp+=value
                    }
                }
                activity!!.tv_ttlHarga.text = temp.toString()
            }
        }
        if (listProduk!!.isEmpty()){
            listProduk!!.addAll(produk)
            produkAdapter = ProdukListAdapter(listProduk, requireContext(), onClickItem, onResult)
            rv_list.adapter = produkAdapter
            rv_list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
        else{
            listProduk!!.clear()
            listProduk!!.addAll(produk)
            listProduk!!.size
            produkAdapter!!.notifyDataSetChanged()
        }

    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loading?.show()
        } else {
            loading?.dismiss()
        }
    }
}