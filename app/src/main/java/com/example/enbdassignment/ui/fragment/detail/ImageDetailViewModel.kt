package com.example.enbdassignment.ui.fragment.detail

import androidx.lifecycle.MutableLiveData
import com.example.enbdassignment.data.models.entities.ImageEntity
import com.example.enbdassignment.ui.fragment.base.BaseViewModel
import javax.inject.Inject

class ImageDetailViewModel @Inject constructor() : BaseViewModel() {

    val imageEntity = MutableLiveData<ImageEntity>()
}