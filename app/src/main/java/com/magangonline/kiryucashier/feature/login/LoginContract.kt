package com.magangonline.kiryucashier.feature.login

import com.magangonline.kiryucashier.base.BasePresenter
import com.magangonline.kiryucashier.base.BaseView

interface LoginContract {

    interface Presenter: BasePresenter {
        fun login(email:String, pass:String)
    }

    interface View : BaseView<Presenter>{
        fun onError(message: String)
        fun onSuccess(message: String)
        fun onPrecess(boolean: Boolean)
    }


}