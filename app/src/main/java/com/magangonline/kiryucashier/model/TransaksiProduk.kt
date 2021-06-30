package com.magangonline.kiryucashier.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransaksiProduk (
        var nama_produk  : String = "",
        var harga_produk : String = "",
        var jumlah_pesanan    : String = "0",
) : Parcelable
