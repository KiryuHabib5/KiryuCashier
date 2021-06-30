package com.magangonline.kiryucashier.feature.register

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.feature.login.LoginActivity
import com.magangonline.kiryucashier.model.User
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterContract.View {
    override lateinit var presenter: RegisterContract.Presenter
    private var loading: ProgressDialog?=null
    init {
        RegisterPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        loading = ProgressDialog(this)
        btn_continue_reg.setOnClickListener {
            if (et_username_reg.text.toString().equals("")){
                et_username_reg.setError("Input Username terlebih dahulu")
            }else if (et_email_reg.text.toString().equals("")){
                et_email_reg.setError("Input Email Terlebih Dahulu")
            }else if (et_tglLahir_reg.text.toString().equals("")){
                et_tglLahir_reg.setError("Input Tangggal Lahir Terlebih Dahulu")
            }else if (et_pass_reg.text.toString().equals("")){
                et_pass_reg.setError("Input Password Terlebih Dahulu")
            }else{

                val user = User(
                    et_username_reg.text.toString(),
                    et_email_reg.text.toString(),
                    et_tglLahir_reg.text.toString(),
                    et_pass_reg.text.toString()
                )
                presenter.register(user)
//            Toast.makeText(this, et_username_reg.text.toString(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onError(message: String) {
        loading?.dismiss()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(message: String) {
        loading?.dismiss()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    override fun onPrecess(boolean: Boolean) {
        loading?.setMessage("loading")
        loading?.setCancelable(false)
        loading?.show()
    }
}