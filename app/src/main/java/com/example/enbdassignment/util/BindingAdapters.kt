package com.example.enbdassignment.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.enbdassignment.di.modules.GlideApp

object BindingAdapters {

    /**
     * Load Image with string url
     */

    @JvmStatic
    @BindingAdapter("imgUrl", "errorPlaceHolderDrawable", requireAll = false)
    fun loadImageStringURL(view: ImageView, url: String?, drawable: Drawable?) {
        url?.takeIf { it.isNotEmpty() }.let { imgUrl ->
            val load = GlideApp.with(view).load(imgUrl)
            drawable?.let {
                load.error(it)
            }
            load.diskCacheStrategy(DiskCacheStrategy.ALL)
            load.into(view)
        }
    }
}