package com.magangonline.kiryucashier.feature.addproduk

import com.magangonline.kiryucashier.base.BasePresenter
import com.magangonline.kiryucashier.base.BaseView
import com.magangonline.kiryucashier.model.Produk

interface AddProdukContract {

    interface Presenter : BasePresenter {
        fun addProduk(produk: Produk)
    }

    interface View : BaseView<Presenter> {
        fun onError(message: String)
        fun onSuccess(message: String)
        fun onProcess(boolean: Boolean)
    }
}