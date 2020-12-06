package id.sharekom.bukutamu.model.repository.login

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.sharekom.bukutamu.model.repository.UserEmailAndPassword

class LoginRepository {
    fun getLoginData(data: UserEmailAndPassword, callback: LoginRepositoryCallback<String?>) {
        Firebase.auth.signInWithEmailAndPassword(data.email, data.password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    callback.onDataLoaded(task.result.user?.isEmailVerified.toString())
                }else {
                    callback.onDataError(task.exception?.message.toString())
                }
            }
    }
}