package com.magangonline.kiryucashier.feature.login

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginPresenter(val context: Context, var view: LoginContract.View?):LoginContract.Presenter {

    init {
        view?.presenter = this
    }

    override fun login(email: String, pass: String) {
       view?.onPrecess(true)
        val firebaseAuth: FirebaseAuth = Firebase.auth
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful){
                view?.onSuccess("Succsess")
            }else {
                view?.onError(it.exception?.message!!)
            }
        }
    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun destroy() {
        view = null
    }

}