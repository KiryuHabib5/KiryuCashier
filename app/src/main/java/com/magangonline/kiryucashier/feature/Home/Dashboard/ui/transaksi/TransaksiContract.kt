package com.magangonline.kiryucashier.feature.Home.Dashboard.ui.transaksi

import com.magangonline.kiryucashier.base.BasePresenter
import com.magangonline.kiryucashier.base.BaseView
import com.magangonline.kiryucashier.feature.Home.Dashboard.ui.transaksi.search.SearchContract
import com.magangonline.kiryucashier.model.NewProduk
import com.magangonline.kiryucashier.model.Produk

interface TransaksiContract {

    interface Presenter:BasePresenter {
        fun getData(text:String)
        fun getProd(text:String, jumlah:String)
    }

    interface View : BaseView<Presenter> {
        fun onError(message: String)
        fun onSuccess(produk: NewProduk)
        fun onProcess(boolean: Boolean)
    }

}