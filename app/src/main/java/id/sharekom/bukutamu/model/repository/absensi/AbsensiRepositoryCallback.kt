package id.sharekom.bukutamu.model.repository.absensi

interface AbsensiRepositoryCallback<T> {
    fun onDataLoaded(data: T?)
    fun onDataError(message: T?)
}