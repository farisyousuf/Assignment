package com.example.enbdassignment.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.enbdassignment.architecture.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}