package com.magangonline.kiryucashier.model

data class RiwayatTransaksi(
    var kode_transaksi:String = "",
    var tanggal_transaksi:String = "",
    var list_produk: ArrayList<TransaksiProduk> = ArrayList(),
    var total_biaya:String = "",
    var input_bayar:String = "",
    var total_kembali:String = ""
)
