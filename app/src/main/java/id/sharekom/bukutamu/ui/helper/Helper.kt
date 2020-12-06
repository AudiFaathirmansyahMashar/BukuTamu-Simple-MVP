package id.sharekom.bukutamu.ui.helper

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.sharekom.bukutamu.R
import java.text.SimpleDateFormat
import java.util.*

object Helper {
    const val FIELD_WARNING = "Field ini tidak boleh kosong"

    fun getCurrentDate(time: Long): String {
        val dayInIndonesia = arrayOf("Senin", "Selasa", "Rabu", "Kamis", "Jum'at", "Sabtu", "Minggu")

        val sdfNumberOfWeek = SimpleDateFormat("u", Locale.getDefault())
        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault())

        return "${dayInIndonesia[sdfNumberOfWeek.format(Date(time)).toInt() - 1]}, ${sdf.format(Date(time))}"
    }

    fun showProgressDialog(context: Context): Dialog {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.progress_layout)
        dialog.setCancelable(false)

        return dialog
    }

    fun googleSignIn(context: Context) : GoogleSignInClient{
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, gso)
    }

    fun signOut(context: Context) {
        Firebase.auth.signOut()
        googleSignIn(context).signOut()
    }
}