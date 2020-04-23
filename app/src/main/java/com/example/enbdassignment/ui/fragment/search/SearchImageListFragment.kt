package com.example.enbdassignment.ui.fragment.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.enbdassignment.BR
import com.example.enbdassignment.R
import com.example.enbdassignment.databinding.FragmentSearchImageListBinding
import com.example.enbdassignment.ui.fragment.base.BaseFragment
import com.example.enbdassignment.ui.listener.PaginationScrollListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_search_image_list.*
import timber.log.Timber
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
                .debounce(SEARCH_DELAY, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { query ->
                    if (query.isNotEmpty()) {
                        viewModel.search(query)
                    }
                })
        subscribeToLiveData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScrollListener()
    }

    private fun initScrollListener() {
        recyclerView?.apply {
            addOnScrollListener(object :
                PaginationScrollListener(layoutManager as LinearLayoutManager) {
                override fun isLastPage(): Boolean = viewModel.allHitsLoaded

                override fun isLoading(): Boolean = viewModel.requestInProgress

                override fun loadMoreItems() = viewModel.search(viewModel.searchString.value ?: "")

            })
        }
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
        private const val SEARCH_DELAY: Long = 600
    }
}