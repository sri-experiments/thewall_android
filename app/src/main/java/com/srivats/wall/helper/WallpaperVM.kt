/*
* WallpaperVM.kt
* Purpose is to create a view model for the wallpapers
*/

package com.srivats.wall.helper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot

class WallpaperVM: ViewModel() {

    private val fRepo: Repo = Repo()

    private val wallpaperList: MutableLiveData<List<WallpaperModel>> by lazy {
        MutableLiveData<List<WallpaperModel>>().also {
            loadWallpaperData()
        }
    }

    fun getWallpaperList(): LiveData<List<WallpaperModel>>{
        return wallpaperList
    }

    fun loadWallpaperData(){
        fRepo.queryWallpaper().addOnCompleteListener {
            if(it.isSuccessful){
                val result = it.result
                if(result!!.isEmpty){

                }
                else{
                    wallpaperList.value = result.toObjects(WallpaperModel::class.java)
                    val lastItem: DocumentSnapshot = result.documents[result.size() - 1]
                    fRepo.lastVisible = lastItem
                }
            }
            else{
                Log.d("View Model Error", "Error: ${it.exception!!.message}")
             }
        }
    }

}