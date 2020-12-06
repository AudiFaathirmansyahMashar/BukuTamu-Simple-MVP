package id.sharekom.bukutamu.ui.register

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import id.sharekom.bukutamu.R
import id.sharekom.bukutamu.databinding.ActivityRegisterBinding
import id.sharekom.bukutamu.model.repository.UserEmailAndPassword
import id.sharekom.bukutamu.model.repository.googlelogin.GoogleLoginRepository
import id.sharekom.bukutamu.model.repository.register.RegisterRepository
import id.sharekom.bukutamu.ui.BarcodeScannerActivity
import id.sharekom.bukutamu.ui.helper.Helper
import id.sharekom.bukutamu.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity(), View.OnClickListener, RegisterView, GoogleLoginView {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var progressBarDialog: Dialog
    private lateinit var googleLoginPresenter: GoogleLoginPresenter
    private lateinit var registerPresenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        googleLoginPresenter = GoogleLoginPresenter(this, GoogleLoginRepository())
        registerPresenter = RegisterPresenter(this, RegisterRepository())
        progressBarDialog = Helper.showProgressDialog(this)

        binding.btnRegDaftar.setOnClickListener(this)
        binding.ibtnRegisterBack.setOnClickListener(this)
        binding.btnRegMasuk.setOnClickListener(this)
        binding.btnRegGoogle.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ibtn_register_back -> {
                onBackPressed()
            }
            R.id.btn_reg_masuk -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            R.id.btn_reg_daftar -> {
                val email = binding.tilRegEmail.editText?.text.toString()
                val password = binding.tilRegPassword.editText?.text.toString()

                registerValidate(email, password)
            }
            R.id.btn_reg_google -> {
                val signInIntent = Helper.googleSignIn(this).signInIntent

                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.result
                googleLoginPresenter.getGoogleLogin(account.idToken!!)
            } catch (e: Exception) {
                Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerValidate(email: String, password: String) {
        var isEmptyState = false

        when {
            email.isEmpty() -> {
                isEmptyState = true
                binding.tilRegEmail.editText?.error = Helper.FIELD_WARNING
            }
            password.isEmpty() -> {
                isEmptyState = true
                binding.tilRegPassword.editText?.error = Helper.FIELD_WARNING
            }
        }

        if (!isEmptyState) {
            registerPresenter.getRegister(UserEmailAndPassword(email, password))
        }
    }

    override fun onShowLoading() {
        progressBarDialog.show()
    }

    override fun onHideLoading() {
        progressBarDialog.dismiss()
    }

    override fun onGoogleDataLoaded(data: String?) {
        startActivity(Intent(this, BarcodeScannerActivity::class.java))
        finishAffinity()
    }

    override fun onGoogleDataError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDataLoaded(data: String?) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
    }

    override fun onDataError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object{
        const val RC_SIGN_IN = 100
    }
}