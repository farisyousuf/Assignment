package com.example.enbdassignment.ui.fragment.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class SearchImageListViewModel @Inject constructor() : ViewModel() {
    var searchString = MutableLiveData<String>()
    var searchSubject: BehaviorSubject<String> = BehaviorSubject.create<String>()

    init {
        searchString.value = DEFAULT_SEARCH_QUERY
    }

    fun search(query: String) {

    }

    companion object {
        private const val DEFAULT_SEARCH_QUERY = "apple"
    }
}