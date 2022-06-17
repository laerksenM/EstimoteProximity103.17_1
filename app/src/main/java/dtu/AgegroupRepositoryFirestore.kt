package dtu


import android.util.Log
import androidx.compose.runtime.toMutableStateList
import com.google.firebase.firestore.FirebaseFirestore
import dtu.core.Constants

 class AgegroupRepositoryFirestore: AgegroupRepository {
    override var agegroup = mutableListOf<Agegroup>().toMutableStateList()

    override fun getAgegroup(ageID: String) {
        val docRef = FirebaseFirestore.getInstance().collection(Constants.AGEGROUP)
        docRef.whereEqualTo(Constants.ZONETAG, ageID)
            .get()
            .addOnSuccessListener { documents ->
                agegroup = documents.toObjects(Agegroup::class.java).toMutableStateList()
                logAgegroup("getAgegroup")
                for (document in documents) {
                    Log.d(Constants.FIREBASETAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(Constants.FIREBASETAG, "Error, kunne ikke faa dokument:", exception)
            }

        //val docRef = db.collection("Beacons").document(Document.toString())
            // tilføj vores database her.
            /*.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(Constants.FIREBASETAG, "Listen failed.", e)
                    //return@addSnapshotListener
                }

                if (snapshot != null) {
                    Log.d(Constants.FIREBASETAG, "snapshot: $snapshot")
                    agegroup = snapshot.toObjects(Agegroup::class.java).toMutableStateList()
                    Log.d(Constants.FIREBASETAG, "Current data size: ${agegroup.size}")
                    logAgegroup()

                } else {
                    Log.d(Constants.FIREBASETAG, "Current data: null")
                }
            }*/
    }

     override fun getAge() {
         TODO("Not yet implemented")
     }

     override fun addListener() {
        FirebaseFirestore.getInstance().collection(Constants.AGEGROUP)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(Constants.FIREBASETAG, "Listen mislykkede", e)
                }

                if (snapshot != null) {
                    agegroup = snapshot.toObjects(Agegroup::class.java).toMutableStateList()
                    logAgegroup("Initial read")

                } else {
                    Log.d(Constants.FIREBASETAG, "Current data: null")
                }
            }
    }

    private fun logAgegroup(comment: String){
        for(Agegroup in agegroup) {
            Log.d(Constants.FIREBASETAG, "$comment: $agegroup")

            //Log.d(Constants.FIREBASETAG, "Age group: ${age}")
        }
    }
}

