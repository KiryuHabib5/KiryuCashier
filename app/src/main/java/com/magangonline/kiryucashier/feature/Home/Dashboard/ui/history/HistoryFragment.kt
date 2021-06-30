package com.magangonline.kiryucashier.feature.Home.Dashboard.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.adapter.ProdukAdapter
import com.magangonline.kiryucashier.adapter.RiwayatAdapter
import com.magangonline.kiryucashier.feature.Home.Dashboard.ui.history.Detail.ProduklistActivity
import com.magangonline.kiryucashier.feature.detailproduk.DetailProdukActivity
import com.magangonline.kiryucashier.model.Produk
import com.magangonline.kiryucashier.model.RiwayatTransaksi
import com.magangonline.kiryucashier.model.TransaksiProduk
import kotlinx.android.synthetic.main.fragment_home.*

class HistoryFragment : Fragment(), HistoryContract.View {

    override lateinit var presenter: HistoryContract.Presenter
    private lateinit var slideshowViewModel: SlideshowViewModel
    private var riwayatAdapter: RiwayatAdapter? = null
    private var listProduk : ArrayList<RiwayatTransaksi>? = null
//    private var searchListProduk : ArrayList<RiwayatTransaksi>? = null
    private var loading: AlertDialog?=null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
                ViewModelProvider(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_riwayat, container, false)
//        val textView: TextView = root.findViewById(R.id.text_slideshow)
//        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HistoryPresenter(requireContext(),this)
        loading = showDialogLoading()
        presenter.getData()
    }


    override fun onError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onSuccess(produk: ArrayList<RiwayatTransaksi>) {
        listProduk = produk
        listProduk!!.size
        val onClickItem = object : RiwayatAdapter.OnClickItem {
            override fun onClickItem(data: ArrayList<TransaksiProduk>) {
                startActivity(
                        Intent(requireContext(), ProduklistActivity::class.java)
                                .putExtra("kode", data)
                )
                activity!!.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

            }

        }
        val delete = object: RiwayatAdapter.Delete{
            override fun onDelete(kode: String) {
                presenter.deleteProduk(kode)
            }

        }

        riwayatAdapter = RiwayatAdapter(produk, requireContext() ,delete, onClickItem)
        rv_produk.adapter = riwayatAdapter
        rv_produk.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onProcess(boolean: Boolean) {
        if (boolean) {
            loading?.show()
        } else {
            loading?.dismiss()
        }
    }

    override fun onSuccessDelete(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showDialogLoading(): AlertDialog {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.item_layout, null, false)
        return AlertDialog.Builder(requireContext())
                .setView(view)
                .setCancelable(false)
                .create()
    }
}