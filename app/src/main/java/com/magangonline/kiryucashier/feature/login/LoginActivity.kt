package com.magangonline.kiryucashier.feature.login

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.magangonline.kiryucashier.feature.Home.Dashboard.MainActivity
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.feature.Home.Dashboard.HomeActivity
import com.magangonline.kiryucashier.feature.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    override lateinit var presenter: LoginContract.Presenter
    private var loading: ProgressDialog? = null

    init {
        LoginPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loading = ProgressDialog(this)
        btn_sign_In.setOnClickListener {
            if (et_email.text.toString().equals("")){
                et_email.setError("Input Username Terlebih dahulu")
            }else if (et_pass.text.toString().equals("")){
                et_pass.setError("Iput Password terlebih dahulu")
            } else{
                presenter.login(et_email.text.toString(), et_pass.text.toString())
            }
        }


        tv_toregister.setOnClickListener {
            val toRegister = Intent(this, RegisterActivity::class.java)
            startActivity(toRegister)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

    }

    override fun onError(message: String) {
        loading?.dismiss()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(message: String) {
        loading?.dismiss()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        //intent.putExtra("email", et_email.text.toString())
        val toHome = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(toHome)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    override fun onPrecess(boolean: Boolean) {
        loading?.setMessage("Tunggu Sebentar")
        loading?.setCancelable(false)
        loading?.show()
    }
}