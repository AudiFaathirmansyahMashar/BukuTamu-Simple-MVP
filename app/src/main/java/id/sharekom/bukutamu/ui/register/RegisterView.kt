package id.sharekom.bukutamu.ui.register

import id.sharekom.bukutamu.model.repository.register.RegisterRepositoryCallback

interface RegisterView: RegisterRepositoryCallback<String?> {
    fun onShowLoading()
    fun onHideLoading()
}