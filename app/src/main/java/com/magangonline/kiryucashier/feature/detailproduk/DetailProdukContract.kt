package com.magangonline.kiryucashier.feature.detailproduk

import com.magangonline.kiryucashier.base.BasePresenter
import com.magangonline.kiryucashier.base.BaseView
import com.magangonline.kiryucashier.model.Produk

interface DetailProdukContract{

    interface Presenter : BasePresenter{
        fun getProduk(id: String?)
        fun updateProduk(id: String, produk: Produk)
        fun deleteProduk(id: String)
    }

    interface View : BaseView<Presenter> {
        fun onError(message: String)
        fun onGetData(produk: Produk, id: String?)
        fun onSuccess(message: String)
        fun onSuccessDelete(message: String)
        fun onProcess(boolean: Boolean)
    }

}