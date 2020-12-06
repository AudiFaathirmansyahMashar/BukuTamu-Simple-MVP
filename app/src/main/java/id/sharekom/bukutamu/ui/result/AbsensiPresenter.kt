package id.sharekom.bukutamu.ui.result

import id.sharekom.bukutamu.model.repository.absensi.AbsensiRepository
import id.sharekom.bukutamu.model.repository.absensi.AbsensiRepositoryCallback

class AbsensiPresenter(private val view: AbsensiView, private val absensiRepository: AbsensiRepository) {
    fun getAbsensi(data: HashMap<String, String>) {
        view.onShowLoading()
        absensiRepository.getAbsensi(data, object : AbsensiRepositoryCallback<String?> {
            override fun onDataLoaded(data: String?) {
                view.onHideLoading()
                view.onDataLoaded(data)
            }

            override fun onDataError(message: String?) {
                view.onHideLoading()
                view.onDataError(message)
            }

        })
    }
}