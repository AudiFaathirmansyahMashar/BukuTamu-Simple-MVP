package id.sharekom.bukutamu.ui.result

import id.sharekom.bukutamu.model.repository.absensi.AbsensiRepositoryCallback

interface AbsensiView : AbsensiRepositoryCallback<String?> {
    fun onShowLoading()
    fun onHideLoading()
}