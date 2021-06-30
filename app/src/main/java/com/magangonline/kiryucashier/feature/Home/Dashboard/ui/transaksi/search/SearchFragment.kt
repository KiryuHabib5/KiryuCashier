package com.magangonline.kiryucashier.feature.Home.Dashboard.ui.transaksi.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.adapter.ProdukAdapter
import com.magangonline.kiryucashier.model.Produk
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : DialogFragment(),SearchContract.View {
    override lateinit var presenter: SearchContract.Presenter
    private var produkAdapter: ProdukAdapter? = null
    private var listProduk : ArrayList<Produk>? = null
    private var searchListProduk : ArrayList<Produk>? = null
    private var by:String = ""
    private var onOptionDialogListener: OnOptionDialogListener? = null
    fun getOnOptionDialogListener():OnOptionDialogListener?{
        return  onOptionDialogListener
    }
    fun setOnOptionDialogListener(onOptionDialogListener: OnOptionDialogListener){
        this.onOptionDialogListener = onOptionDialogListener
    }
//    private var loading: AlertDialog?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        loading = showDialogLoading()
        SearchPresenter(requireContext(), this)
        presenter.getData()
        by = requireArguments().getString("by", "nama")
        search.addTextChangedListener(onSearchChange(by))
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
                        getOnOptionDialogListener()!!.onOptionChoosen(data.kode_produk)
                        requireDialog().dismiss()
//                        startActivity(
//                            Intent(requireContext(), MainActivity::class.java)
//                                .putExtra("kode", data.kode_produk)
//                        )
//                        val bundle:Bundle = Bundle()
////                        val fragment:Fragment = TransaksiFragment()
////                        bundle.putString("kode", data.kode_produk)
////                        fragment.arguments = bundle
////                        activity?.supportFragmentManager!!
////                                .beginTransaction()
////                                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
////                                .replace(R.id.content, fragment)
////                                .commit()
//                        Toast.makeText(requireContext(), "text", Toast.LENGTH_SHORT).show()
//                        getOnOptionDialogListener()!!.onOptionChoosen(data.kode_produk)
//                        requireDialog().dismiss()
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

    private fun filterProduk(search: String, by: String) : ArrayList<Produk> {
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
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onSuccess(produk: ArrayList<Produk>) {
        listProduk = produk
        val onClickItem = object : ProdukAdapter.OnClickItem {
            override fun onClickItem(data: Produk) {
                getOnOptionDialogListener()!!.onOptionChoosen(data.kode_produk)
                requireDialog().dismiss()
            }

        }

        produkAdapter = ProdukAdapter(produk, requireContext(), onClickItem)
        rv_produk.adapter = produkAdapter
        rv_produk.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
//            loading?.show()
        } else {
//            loading?.dismiss()
        }
    }
    public interface OnOptionDialogListener {
        fun onOptionChoosen(text: String?)
    }

}