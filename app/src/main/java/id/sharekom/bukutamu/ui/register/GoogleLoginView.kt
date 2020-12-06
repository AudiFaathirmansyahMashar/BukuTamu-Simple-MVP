package id.sharekom.bukutamu.ui.register

import id.sharekom.bukutamu.model.repository.googlelogin.GoogleLoginRepositoryCallback

interface GoogleLoginView : GoogleLoginRepositoryCallback<String?> {
    fun onShowLoading()
    fun onHideLoading()
}