package com.magangonline.kiryucashier.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewProduk (
        var kode_produk  : String = "",
        var nama_produk  : String = "",
        var harga_produk : String = "",
        var qty_produk   : String = "",
        var itemClick    : String = "0"
) : Parcelable
