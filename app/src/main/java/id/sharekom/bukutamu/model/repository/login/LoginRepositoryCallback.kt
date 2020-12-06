package id.sharekom.bukutamu.model.repository.login

interface LoginRepositoryCallback<T> {
    fun onDataLoaded(data: T?)
    fun onDataError(message: T?)
}