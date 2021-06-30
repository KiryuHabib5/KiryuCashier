package com.magangonline.kiryucashier.feature.register

import com.magangonline.kiryucashier.model.User
import com.magangonline.kiryucashier.base.BasePresenter
import com.magangonline.kiryucashier.base.BaseView

interface RegisterContract {

    interface View : BaseView<Presenter> {
        fun onError(message: String)
        fun onSuccess(message: String)
        fun onPrecess(boolean: Boolean)
    }

    interface Presenter : BasePresenter {
        fun register(user: User)
    }
}