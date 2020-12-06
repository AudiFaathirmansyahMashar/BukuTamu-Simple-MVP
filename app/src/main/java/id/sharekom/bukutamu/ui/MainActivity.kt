package id.sharekom.bukutamu.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.sharekom.bukutamu.R
import id.sharekom.bukutamu.databinding.ActivityMainBinding
import id.sharekom.bukutamu.ui.login.LoginActivity
import id.sharekom.bukutamu.ui.register.RegisterActivity


class MainActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        doRequestPermission()

        binding.btnMasuk.setOnClickListener(this)
        binding.btnDaftar.setOnClickListener(this)

        checkLogin()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_masuk -> {
                val intentLogin = Intent(this, LoginActivity::class.java)
                startActivity(intentLogin)
            }
            R.id.btn_daftar -> {
                val intentRegister = Intent(this, RegisterActivity::class.java)
                startActivity(intentRegister)
            }
        }
    }

    private fun doRequestPermission() {
        if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            AlertDialog.Builder(this)
                    .setTitle("Perizinan Lokasi")
                    .setMessage("Izinkan aplikasi untuk mengakses lokasi?")
                    .setPositiveButton("Ya") { _, _ ->
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 44)
                    }
                    .setNegativeButton("Tidak") { _, _ ->
                        finish()
                    }
                    .show()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder(this)
                        .setTitle("Perizinan Kamera")
                        .setMessage("Izinkan aplikasi untuk mengakses kamera?")
                        .setPositiveButton("Ya") { _, _ ->
                            requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
                        }
                        .setNegativeButton("Tidak") { _, _ ->
                            finish()
                        }
                        .show()
            }
        }
    }

    private fun checkLogin() {
        when {
            Firebase.auth.currentUser?.isEmailVerified == true -> {
                startActivity(Intent(this, BarcodeScannerActivity::class.java))
                finish()
            }
            GoogleSignIn.getLastSignedInAccount(this) != null -> {
                startActivity(Intent(this, BarcodeScannerActivity::class.java))
                finish()
            }
        }
    }
}