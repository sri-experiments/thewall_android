/*
* HomeFragment.kt
* Purpose is to display the wallpapers that are available to be set
*/

package com.srivats.wall

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.srivats.wall.helper.WallpaperModel
import com.srivats.wall.helper.WallpaperVM
import com.srivats.wall.databinding.FragmentFirstBinding
import com.srivats.wall.helper.Repo

class HomeFragment : Fragment(), (WallpaperModel) -> Unit {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var isLoading: Boolean = true
    private val fRepo: Repo = Repo()

    private var wallpaperList: List<WallpaperModel> = ArrayList()

    val wallpaperVM: WallpaperVM by viewModels()
    private val wallpaperAdapter: WallpaperAdapter = WallpaperAdapter(
        wallpaperList,
        this
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding!!.wallpaperRv.setHasFixedSize(true)
        _binding!!.wallpaperRv.layoutManager = GridLayoutManager(context, 3)
        _binding!!.wallpaperRv.adapter = wallpaperAdapter

        _binding!!.wallpaperRv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView.canScrollVertically(1)
                    && newState == RecyclerView.SCROLL_STATE_IDLE){
                    if(!isLoading){
                        wallpaperVM.loadWallpaperData()
                        isLoading = true
//                        wallpaperVM.loadWallpaperData()
//                        isLoading = true
                    }
                }
            }
        })

        wallpaperVM.getWallpaperList().observe(viewLifecycleOwner) {
            wallpaperList = it
            wallpaperAdapter.wallpaperList = wallpaperList
            wallpaperAdapter.notifyDataSetChanged()

            isLoading = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun invoke(wallpaper: WallpaperModel) {
        val action = HomeFragmentDirections.actionFirstFragmentToSecondFragment(
            wallpaper.imgUrl,
            wallpaper.capturedBy,
            wallpaper.description,
            wallpaper.collection,
            wallpaper.id,
        )
        findNavController().navigate(action)
    }
}