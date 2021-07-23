package com.magangonline.kiryucashier.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Produk (
        var kode_produk: String = "",
        var nama_produk: String = "",
        var harga_produk: String = "",
        var qty_produk: String = "",
) : Parcelable

