package id.sharekom.bukutamu.ui.login

import id.sharekom.bukutamu.model.repository.UserEmailAndPassword
import id.sharekom.bukutamu.model.repository.login.LoginRepository
import id.sharekom.bukutamu.model.repository.login.LoginRepositoryCallback

class LoginPresenter(private val view: LoginView, private val repository: LoginRepository) {
    fun getLogin(data: UserEmailAndPassword){
        view.onShowLoading()
        repository.getLoginData(data, object : LoginRepositoryCallback<String?>{
            override fun onDataLoaded(data: String?) {
                view.onHideLoading()
                view.onDataLoaded(data)
            }

            override fun onDataError(message: String?) {
                view.onHideLoading()
                view.onDataLoaded(message)
            }
        })
    }
}