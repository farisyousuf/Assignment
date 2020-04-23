package com.example.enbdassignment.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.enbdassignment.architecture.ViewModelFactory
import com.example.enbdassignment.di.key.ViewModelKey
import com.example.enbdassignment.ui.fragment.detail.ImageDetailViewModel
import com.example.enbdassignment.ui.fragment.search.SearchImageListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SearchImageListViewModel::class)
    internal abstract fun bindSearchImageListViewModel(viewModel: SearchImageListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ImageDetailViewModel::class)
    internal abstract fun bindImageDetailViewModel(viewModel: ImageDetailViewModel): ViewModel
}