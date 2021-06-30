package com.magangonline.kiryucashier.feature.Home.Profile

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.feature.start.StartActivity
import com.magangonline.kiryucashier.model.Produk
import com.magangonline.kiryucashier.model.User
import kotlinx.android.synthetic.main.activity_profile.*
//import kotlinx.android.synthetic.main.activity_profile.btn_update

class ProfileActivity : AppCompatActivity(), ProfileContract.View {

    override lateinit var presenter: ProfileContract.Presenter
    private var loading: ProgressDialog? = null
//    private var email:String? = null
//    private var pass:String = ""
    init {
        ProfilePresenter(this,this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        loading = ProgressDialog(this)
        presenter.getUser()
        btn_logOut.setOnClickListener {
            loading?.show()
            Firebase.auth.signOut()
            Toast.makeText(this, "Log Out Success", Toast.LENGTH_LONG).show()
            loading?.dismiss()
            val i = Intent(this, StartActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(i)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }

//        btn_update.setOnClickListener {
//            loading?.show()
//            if(et_email.text.toString().equals("")){
//                et_email.setError("Harus diisi!!")
//            }else if(et_nama.text.toString().equals("")){
//                et_nama.setError("Harus diisi!!")
//            }else if(et_date.text.toString().equals("")){
//                et_date.setError("Harus diisi!!")
//            }else {
//                val user = User(
//                        et_nama.text.toString(),
//                        et_email.text.toString(),
//                        et_date.text.toString(),
//                        pass
//                )
//                presenter.updateUSer(email!!, user)
//            }
//        }


    }



    override fun onError(message: String) {
        loading?.dismiss()
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onSuccess(user: User, message: String) {
//        email = user.email
//        pass = user.password
        loading?.dismiss()
        et_nama.setText(user.name)
        et_email.setText(user.email)
        et_date.setText(user.tglLahir)
    }

    override fun onProcess(boolean: Boolean) {
        loading?.setMessage("Tunggu Sebentar")
        loading?.setCancelable(false)
        loading?.show()
    }

}