/*
* WallpaperAdapter.kt
* Purpose is to load the data into view
* */

package com.srivats.wall

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.srivats.wall.helper.WallpaperModel

class WallpaperAdapter(
    var wallpaperList: List<WallpaperModel>,
    private val clickListener: (WallpaperModel) -> Unit
): RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder>() {

    class WallpaperViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(wallpapers: WallpaperModel, clickListener: (WallpaperModel) -> Unit){
            Glide.with(itemView.context)
                .load(wallpapers.imgUrl)
                .into(itemView.findViewById(R.id.individual_iv))

            itemView.setOnClickListener{
                clickListener(wallpapers)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WallpaperViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.individual_item,
        parent, false)
        return WallpaperViewHolder(view)
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        holder.bind(wallpaperList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return  wallpaperList.size
    }

}