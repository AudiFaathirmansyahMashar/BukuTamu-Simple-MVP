package id.sharekom.bukutamu.ui.result

import android.text.Editable
import android.widget.EditText

data class InputView(
    val date: EditText?,
    val nama: EditText?,
    val dituju: EditText?,
    val jabatan: EditText?,
    val telp: EditText?,
    val keperluan: EditText?
)