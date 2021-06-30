package com.magangonline.kiryucashier.feature.Home.Dashboard.ui.history

import com.magangonline.kiryucashier.base.BasePresenter
import com.magangonline.kiryucashier.base.BaseView
import com.magangonline.kiryucashier.model.RiwayatTransaksi

interface HistoryContract {
    interface Presenter: BasePresenter {
        fun getData()
        fun deleteProduk(id: String)
    }

    interface View : BaseView<Presenter> {
        fun onError(message: String)
        fun onSuccess(produk: ArrayList<RiwayatTransaksi>)
        fun onProcess(boolean: Boolean)
        fun onSuccessDelete(message: String)
    }
}