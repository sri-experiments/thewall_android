/*
* Repo.kt
* Purpose is to make a db call to Firebase Firestore to get the data
*/

package com.srivats.wall.helper

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestoreSettings

class  Repo {

    private val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()

    var lastVisible: DocumentSnapshot? = null
    private val itemsPerPage: Long = 6

    fun queryWallpaper(): Task<QuerySnapshot> {

        if(lastVisible == null){
            return fStore.collection("wall")
                .whereEqualTo("type", "mobile").get()
        } else{
            return fStore.collection("wall")
                .whereEqualTo("type", "mobile").get()
        }
    }
}