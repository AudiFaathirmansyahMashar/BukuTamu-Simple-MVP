package id.sharekom.bukutamu.model.repository.register

interface RegisterRepositoryCallback<T> {
    fun onDataLoaded(data: T?)
    fun onDataError(message: T?)
}