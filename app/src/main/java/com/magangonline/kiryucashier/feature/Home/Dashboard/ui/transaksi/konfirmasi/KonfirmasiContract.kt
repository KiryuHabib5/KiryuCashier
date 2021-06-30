package com.magangonline.kiryucashier.feature.Home.Dashboard.ui.transaksi.konfirmasi

import com.magangonline.kiryucashier.base.BasePresenter
import com.magangonline.kiryucashier.base.BaseView
import com.magangonline.kiryucashier.model.RiwayatTransaksi
import com.magangonline.kiryucashier.model.TransaksiProduk

interface KonfirmasiContract {
    interface Presenter: BasePresenter {
        fun save(item:RiwayatTransaksi)
    }

    interface View : BaseView<Presenter> {
        fun onError(message: String)
        fun onSuccess(message: String)
        fun onProcess(boolean: Boolean)
    }
}