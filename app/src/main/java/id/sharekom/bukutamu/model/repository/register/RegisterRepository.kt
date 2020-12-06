package id.sharekom.bukutamu.model.repository.register

import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import id.sharekom.bukutamu.model.repository.UserEmailAndPassword

class RegisterRepository {
    fun getRegister(data: UserEmailAndPassword, callback: RegisterRepositoryCallback<String?>){
        Firebase.auth.createUserWithEmailAndPassword(data.email, data.password)
            .addOnCompleteListener {task->
                if (task.isSuccessful) {
                    val user = task.result.user
                    if (user?.isEmailVerified == false) {
                        user.sendEmailVerification().addOnCompleteListener{ task_verify ->
                            if (task_verify.isSuccessful) {
                                callback.onDataLoaded("Email verifikasi telah dikirim")
                            } else {
                                callback.onDataError(task_verify.exception?.message.toString())
                            }
                        }
                    }
                }else {
                    callback.onDataError(task.exception?.message.toString())
                }
            }
    }
}