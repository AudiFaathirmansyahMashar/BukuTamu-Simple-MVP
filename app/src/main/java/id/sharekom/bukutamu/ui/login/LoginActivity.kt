package id.sharekom.bukutamu.ui.login

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.sharekom.bukutamu.R
import id.sharekom.bukutamu.databinding.ActivityLoginBinding
import id.sharekom.bukutamu.model.repository.UserEmailAndPassword
import id.sharekom.bukutamu.model.repository.login.LoginRepository
import id.sharekom.bukutamu.ui.BarcodeScannerActivity
import id.sharekom.bukutamu.ui.helper.Helper
import id.sharekom.bukutamu.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener, LoginView {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginPresenter: LoginPresenter
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        loginPresenter = LoginPresenter(this, LoginRepository())

        dialog = Helper.showProgressDialog(this)

        binding.ivLoginBack.setOnClickListener(this)
        binding.btnLoginDaftar.setOnClickListener(this)
        binding.btnLoginMasuk.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_login_back -> {
                onBackPressed()
            }
            R.id.btn_login_daftar -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
            R.id.btn_login_masuk -> {
                val email: String = binding.tilLoginEmail.editText?.text.toString().trim()
                val password: String = binding.tilLoginPassword.editText?.text.toString().trim()

                editTextEmptyVerify(email, password)
            }
        }
    }

    private fun editTextEmptyVerify(email: String, password: String) {
        var isEmptyState = false

        when {
            email.isEmpty() -> {
                isEmptyState = true
                binding.tilLoginEmail.editText?.error = Helper.FIELD_WARNING
            }
            password.isEmpty() -> {
                isEmptyState = true
                binding.tilLoginPassword.editText?.error = Helper.FIELD_WARNING
            }
        }

        if (!isEmptyState) {
            loginPresenter.getLogin(UserEmailAndPassword(email, password))
        }
    }

    override fun onShowLoading() {
        dialog.show()
    }

    override fun onHideLoading() {
        dialog.dismiss()
    }

    override fun onDataLoaded(data: String?) {
        if (data.toBoolean()) {
            startActivity(Intent(this, BarcodeScannerActivity::class.java))
            finishAffinity()
        } else {
            Toast.makeText(
                this,
                "Email anda belum terdaftar atau belum terverifikasi",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDataError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}