/*
* DetailFragment.kt
* Purpose is to load details of the selected wallpaper and to set the wallpaper
*/

package com.srivats.wall

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.srivats.wall.databinding.FragmentSecondBinding
import org.w3c.dom.Document

class DetailFragment : Fragment(), View.OnClickListener {

    private var imgUrl: String? = null

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("ID", DetailFragmentArgs.fromBundle(requireArguments()).id)

        imgUrl = DetailFragmentArgs.fromBundle(requireArguments()).imgUrl

        Glide.with(requireContext())
            .load(imgUrl).into(binding.wallpaperDet)

        binding.downloadBtn.setOnClickListener(this)
        binding.capturedBy.text = DetailFragmentArgs.fromBundle(requireArguments()).capturedBy
        binding.desc.text = DetailFragmentArgs.fromBundle(requireArguments()).desc
        binding.collection.text = DetailFragmentArgs.fromBundle(requireArguments()).collection
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.downloadBtn -> setWallpaper()
        }
    }

    private fun setWallpaper() {
        val bitmap: Bitmap = binding.wallpaperDet.drawable.toBitmap()

        val wallpaperMgr: WallpaperManager = WallpaperManager.getInstance(context)
        wallpaperMgr.setBitmap(bitmap)

        var wallpaperRef: DocumentReference = FirebaseFirestore.getInstance().collection("wall")
            .document(DetailFragmentArgs.fromBundle(requireArguments()).id)

        wallpaperRef.update("downloadCounter", FieldValue.increment(1))

        val successToast: Toast = Toast.makeText(context, "Wallpaper Set", Toast.LENGTH_SHORT)
        successToast.show()
    }
}

