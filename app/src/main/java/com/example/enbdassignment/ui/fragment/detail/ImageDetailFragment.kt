package com.example.enbdassignment.ui.fragment.detail

import android.os.Bundle
import com.example.enbdassignment.BR
import com.example.enbdassignment.R
import com.example.enbdassignment.databinding.FragmentImageDetailBinding
import com.example.enbdassignment.ui.fragment.base.BaseFragment

class ImageDetailFragment : BaseFragment<FragmentImageDetailBinding, ImageDetailViewModel>() {
    override fun provideViewModelClass(): Class<ImageDetailViewModel> {
        return ImageDetailViewModel::class.java
    }

    override fun layoutId(): Int {
        return R.layout.fragment_image_detail
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val safeArgs = ImageDetailFragmentArgs.fromBundle(it)
            viewModel.imageEntity.value = safeArgs.imageData
        }
    }
}