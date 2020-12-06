package id.sharekom.bukutamu.ui.result

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.sharekom.bukutamu.R
import id.sharekom.bukutamu.databinding.ActivityAbsensiBinding
import id.sharekom.bukutamu.model.repository.absensi.AbsensiRepository
import id.sharekom.bukutamu.ui.helper.Helper
import java.util.*

class AbsensiActivity : AppCompatActivity(), View.OnClickListener, AbsensiView {
    private lateinit var binding: ActivityAbsensiBinding
    private lateinit var dialog: Dialog
    private lateinit var auth: FirebaseAuth
    private lateinit var absensiPresenter: AbsensiPresenter
    private lateinit var inputView: InputView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAbsensiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        inputView = InputView(
            binding.tilAbsensiDate.editText,
            binding.tilAbsensiNama.editText,
            binding.tilAbsensiTuju.editText,
            binding.tilAbsensiJabatan.editText,
            binding.tilAbsensiTelp.editText,
            binding.tilAbsensiKeperluan.editText
        )

        auth = Firebase.auth
        dialog = Helper.showProgressDialog(this)
        absensiPresenter = AbsensiPresenter(this, AbsensiRepository())

        binding.ivAbsensiBack.setOnClickListener(this)
        binding.btnAbsenInput.setOnClickListener(this)
        binding.btnAbsenBersihkan.setOnClickListener(this)

        binding.tilAbsensiNama.editText?.setText(auth.currentUser?.displayName)
    }

    private fun inputAbsensi(
        date: String,
        nama: String,
        dituju: String,
        jabatan: String,
        telp: String,
        keperluan: String
    ) {
        absensiPresenter.getAbsensi(
            hashMapOf(
                "hari/tanggal" to date,
                "nama" to nama,
                "pejabat/staf yang dituju" to dituju,
                "jabatan" to jabatan,
                "no. telp/hp" to telp,
                "keperluan" to keperluan
            )
        )
    }

    override fun onShowLoading() {
        dialog.show()
    }

    override fun onHideLoading() {
        dialog.dismiss()
    }

    override fun onDataLoaded(data: String?) {
        if (data != null) {
            binding.tilAbsensiDate.editText?.setText(Helper.getCurrentDate(data.toLong()))
            Toast.makeText(this, Helper.getCurrentDate(data.toLong()), Toast.LENGTH_SHORT).show()
            inputClear()
        }
    }

    override fun onDataError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_absensi_back -> {
                onBackPressed()
            }
            R.id.btn_absen_input -> {
                inputValidate()
            }
            R.id.btn_absen_bersihkan -> {
                inputClear()
            }
        }
    }

    private fun inputClear() {
        inputView.dituju?.text?.clear()
        inputView.jabatan?.text?.clear()
        inputView.telp?.text?.clear()
        inputView.keperluan?.text?.clear()
    }

    private fun inputValidate() {
        val namaState = inputView.nama?.text.toString().trim()
        val ditujuState = inputView.dituju?.text.toString().trim()
        val jabatanState = inputView.jabatan?.text.toString().trim()
        val telpState = inputView.telp?.text.toString().trim()
        val keperluanState = inputView.keperluan?.text.toString().trim()

        var isEmptyFields = false

        when {
            namaState.isEmpty() -> {
                isEmptyFields = true
                inputView.nama?.error = Helper.FIELD_WARNING
            }
            ditujuState.isEmpty() -> {
                isEmptyFields = true
                inputView.dituju?.error = Helper.FIELD_WARNING
            }
            jabatanState.isEmpty() -> {
                isEmptyFields = true
                inputView.jabatan?.error = Helper.FIELD_WARNING
            }
            telpState.isEmpty() -> {
                isEmptyFields = true
                inputView.telp?.error = Helper.FIELD_WARNING
            }
            keperluanState.isEmpty() -> {
                isEmptyFields = true
                inputView.keperluan?.error = Helper.FIELD_WARNING
            }
        }

        if (!isEmptyFields) {
            inputAbsensi(Date().time.toString(), namaState, ditujuState, jabatanState, telpState, keperluanState)
        }
    }
}