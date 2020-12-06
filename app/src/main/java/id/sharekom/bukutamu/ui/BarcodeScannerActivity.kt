package id.sharekom.bukutamu.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.zxing.Result
import id.sharekom.bukutamu.R
import id.sharekom.bukutamu.databinding.ActivityBarcodeScannerBinding
import id.sharekom.bukutamu.ui.helper.Helper
import id.sharekom.bukutamu.ui.result.AbsensiActivity
import me.dm7.barcodescanner.zxing.ZXingScannerView

class BarcodeScannerActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private lateinit var binding: ActivityBarcodeScannerBinding
    private lateinit var mScannerView: ZXingScannerView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarcodeScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initScannerView()
    }

    private fun checkLocationDistance() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val diskominfo = Location("Diskominfo")
                    diskominfo.latitude = -3.962063
                    diskominfo.longitude = 122.540044

                    val distance = location.distanceTo(location)

                    if (distance < 100) {
                        mScannerView.startCamera()
                    } else {
                        AlertDialog.Builder(this)
                            .setCancelable(false)
                            .setTitle("Konfirmasi Radius")
                            .setMessage("Anda harus berada pada radius 100 meter dari lokasi untuk menggunakan aplikasi ini.")
                            .setPositiveButton("Keluar") { _,_ ->
                                finishAffinity()
                            }
                            .setNegativeButton("Sign Out") { _, _ ->
                                signOut()
                            }
                            .show()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        checkLocationDistance()
    }

    override fun onPause() {
        mScannerView.stopCamera()
        super.onPause()
    }

    override fun handleResult(result: Result?) {
        mScannerView.resumeCameraPreview(this)
        startActivity(Intent(this, AbsensiActivity::class.java))
    }

    private fun initScannerView() {
        mScannerView = ZXingScannerView(this)
        mScannerView.setAutoFocus(true)
        mScannerView.setResultHandler(this)
        binding.frameLayoutCamera.addView(mScannerView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun signOut() {
        Helper.signOut(this)

        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_sign_out) {
            mScannerView.stopCamera()
            AlertDialog.Builder(this)
                .setTitle("Yakin?")
                .setMessage("Apakah anda yakin untuk SignOut?")
                .setPositiveButton("Ya") { _, _ ->
                    signOut()
                }
                .setNegativeButton("Tidak") { _, _ ->
                    mScannerView.startCamera()
                }
                .setOnCancelListener {
                    mScannerView.startCamera()
                }
                .show()
        }

        return super.onOptionsItemSelected(item)
    }
}