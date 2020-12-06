package id.sharekom.bukutamu.ui.login

import id.sharekom.bukutamu.model.repository.login.LoginRepositoryCallback

interface LoginView: LoginRepositoryCallback<String?> {
    fun onShowLoading()
    fun onHideLoading()
}