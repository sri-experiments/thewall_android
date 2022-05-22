/*
* WallpaperModel.kt
* Model class for a wallpaper object
*/

package com.srivats.wall.helper

data class WallpaperModel(
    val capturedBy: String = "",
    val collection: String = "", val description: String = "",
    val downloadCounter: Int = 0, val downloadName: String = "",
    val id: String = "", val imgUrl: String = "",
    val type: String = ""


)