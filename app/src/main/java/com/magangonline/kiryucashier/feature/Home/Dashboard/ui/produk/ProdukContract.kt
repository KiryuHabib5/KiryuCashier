package com.magangonline.kiryucashier.feature.Home.Dashboard.ui.produk

import com.magangonline.kiryucashier.base.BasePresenter
import com.magangonline.kiryucashier.base.BaseView
import com.magangonline.kiryucashier.model.Produk

interface ProdukContract {

    interface Presenter :BasePresenter {
        fun getData()
    }

    interface View : BaseView<Presenter>{
        fun onError(message: String)
        fun onSuccess(produk: ArrayList<Produk>)
        fun onProcess(booblean: Boolean)
    }
}