package id.sharekom.bukutamu.ui.register

import id.sharekom.bukutamu.model.repository.UserEmailAndPassword
import id.sharekom.bukutamu.model.repository.register.RegisterRepository
import id.sharekom.bukutamu.model.repository.register.RegisterRepositoryCallback

class RegisterPresenter(private val view:RegisterView, private val repository: RegisterRepository) {
    fun getRegister(data: UserEmailAndPassword){
        view.onShowLoading()
        repository.getRegister(data, object : RegisterRepositoryCallback<String?>{
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