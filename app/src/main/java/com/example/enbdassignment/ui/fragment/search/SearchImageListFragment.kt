package com.example.enbdassignment.ui.fragment.search

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.enbdassignment.BR
import com.example.enbdassignment.R
import com.example.enbdassignment.databinding.FragmentSearchImageListBinding
import com.example.enbdassignment.ui.fragment.base.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SearchImageListFragment :
    BaseFragment<FragmentSearchImageListBinding, SearchImageListViewModel>() {

    override fun provideViewModelClass(): Class<SearchImageListViewModel> {
        return SearchImageListViewModel::class.java
    }

    override fun layoutId(): Int {
        return R.layout.fragment_search_image_list
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable.add(
            viewModel.searchSubject
                .skip(MIN_COUNT)
                .debounce(SEARCH_DELAY, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { query ->
                    viewModel.search(query)
                })
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.searchString.observe(this, Observer {
            viewModel.searchSubject.onNext(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    companion object {
        private const val MIN_COUNT: Long = 2
        private const val SEARCH_DELAY: Long = 600
    }
}