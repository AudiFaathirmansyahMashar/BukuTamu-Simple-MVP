package id.sharekom.bukutamu.model.repository.googlelogin

import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class GoogleLoginRepository {
    fun getLoginData(idToken: String, callback: GoogleLoginRepositoryCallback<String?>) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback.onGoogleDataLoaded(task.result.user?.displayName)
                } else {
                    callback.onGoogleDataError(task.exception?.message.toString())
                }
            }
    }
}