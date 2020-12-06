package id.sharekom.bukutamu.model.repository.googlelogin

interface GoogleLoginRepositoryCallback<T> {
    fun onGoogleDataLoaded(data: T?)
    fun onGoogleDataError(message: T?)
}