package com.example.enbdassignment.di.modules

import com.example.enbdassignment.ui.fragment.search.SearchImageListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun constributeSearchImageListFragment(): SearchImageListFragment
}