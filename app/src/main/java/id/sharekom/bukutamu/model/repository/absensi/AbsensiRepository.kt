package id.sharekom.bukutamu.model.repository.absensi

import com.google.firebase.firestore.FirebaseFirestore

class AbsensiRepository {
    fun getAbsensi(data: HashMap<String, String>, callback: AbsensiRepositoryCallback<String?>) {
        val firebaseInstance = FirebaseFirestore.getInstance()
        val title = "buku_tamu"
        firebaseInstance.collection(title)
            .add(data)
            .addOnSuccessListener {
                firebaseInstance.collection(title).document(it.id).get()
                    .addOnSuccessListener { document ->
                        callback.onDataLoaded(document.data?.get("hari/tanggal").toString())
                    }
                    .addOnFailureListener { e ->
                        callback.onDataError(e.message.toString())
                    }
            }
            .addOnFailureListener {
                callback.onDataError(it.message.toString())
            }
    }
}