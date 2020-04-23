package com.example.enbdassignment.ui.listener

import com.example.enbdassignment.data.models.entities.ImageEntity

interface OnItemClickListener {
    fun onItemClicked(imageEntity: ImageEntity?)
}