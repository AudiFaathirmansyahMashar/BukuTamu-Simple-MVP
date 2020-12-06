package id.sharekom.bukutamu.ui.register

import id.sharekom.bukutamu.model.repository.googlelogin.GoogleLoginRepository
import id.sharekom.bukutamu.model.repository.googlelogin.GoogleLoginRepositoryCallback

class GoogleLoginPresenter(private val view: GoogleLoginView, private val repository: GoogleLoginRepository) {
    fun getGoogleLogin(idToken: String) {
        view.onShowLoading()
        repository.getLoginData(idToken, object : GoogleLoginRepositoryCallback<String?>{
            override fun onGoogleDataLoaded(data: String?) {
                view.onHideLoading()
                view.onGoogleDataLoaded(data)
            }

            override fun onGoogleDataError(message: String?) {
                view.onHideLoading()
                view.onGoogleDataError(message)
            }

        })
    }
}