package com.magangonline.kiryucashier.feature.Home.Profile

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.magangonline.kiryucashier.model.Produk
import com.magangonline.kiryucashier.model.User

class ProfilePresenter(val context: Context, var view: ProfileContract.View?): ProfileContract.Presenter {

    init {
        view?.presenter = this
    }

    override fun getUser() {
        view?.onProcess(true)
        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("User")
        val currentUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val users : ArrayList<User>? = null
                for (dataSnap in snapshot.children){
                    val user = dataSnap.getValue(User::class.java)
                    users?.add(user!!)
                    if (currentUser?.email.equals(user?.email)){
                        view?.onSuccess(user!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                view?.onError(error.message)
            }

        })
    }

//    override fun updateUSer(email: String, user: User) {
//        view?.onProcess(true)
//        val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("User")
//        ref.child(email).setValue(user).addOnCompleteListener {
//            view?.onSuccess(user,"Success")
//            view?.onProcess(false)
//        }.addOnFailureListener {
//            view?.onError(it.message!!)
//            view?.onProcess(false)
//        }
//    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun destroy() {
        view = null
    }
}