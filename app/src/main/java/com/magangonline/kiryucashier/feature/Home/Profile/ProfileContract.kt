package com.magangonline.kiryucashier.feature.Home.Profile

import com.magangonline.kiryucashier.base.BasePresenter
import com.magangonline.kiryucashier.base.BaseView
import com.magangonline.kiryucashier.model.User

interface ProfileContract {

    interface Presenter : BasePresenter{
        fun getUser()
        //fun updateUSer(email: String, user: User)
    }

    interface View : BaseView<Presenter>{
        fun onError(message: String)
        fun onProcess(boolean: Boolean)
        fun onSuccess(user :User, message: String="")
    }
}