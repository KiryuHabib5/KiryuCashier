package com.magangonline.kiryucashier.feature.register

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.magangonline.kiryucashier.model.User

class RegisterPresenter(val context: Context, var view:RegisterContract.View?) : RegisterContract.Presenter {

    init {
        view?.presenter = this
    }

    override fun register(user: User) {
        view?.onPrecess(true)
        val firebaseAuth : FirebaseAuth = Firebase.auth
        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("User")
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener { it->
            if(it.isSuccessful){
                ref.push().setValue(user).addOnCompleteListener {
                    view?.onSuccess("Success")
                }.addOnFailureListener{
                    view?.onError(it.message!!)
                }
            } else {
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