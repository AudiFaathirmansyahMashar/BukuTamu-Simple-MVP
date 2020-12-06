package id.sharekom.bukutamu.model.repository.absensi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AbsensiData(
    val date: String? = "",
    val nama: String? = "",
): Parcelable
