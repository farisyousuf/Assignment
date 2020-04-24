package com.example.enbdassignment.ui.fragment.search

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.enbdassignment.BR
import com.example.enbdassignment.R
import com.example.enbdassignment.architecture.SingleLiveEvent
import com.example.enbdassignment.data.models.common.ResultState
import com.example.enbdassignment.data.models.entities.ImageEntity
import com.example.enbdassignment.data.repository.ImageRepository
import com.example.enbdassignment.ui.fragment.base.BaseViewModel
import com.example.enbdassignment.ui.listener.OnItemClickListener
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.tatarka.bindingcollectionadapter2.ItemBinding
import javax.inject.Inject

class SearchImageListViewModel @Inject constructor(private val imageRepository: ImageRepository) :
    BaseViewModel() {
    var allHitsLoaded: Boolean = false
    var requestInProgress: Boolean = false
    private var pageNumber = 1
    var lastSearchedWord: String? = null
    var searchString = MutableLiveData<String>()
    var searchFailEvent = SingleLiveEvent<Void>()
    var searchSuccessEvent = SingleLiveEvent<List<ImageEntity>>()
    var onItemClickEvent = SingleLiveEvent<ImageEntity>()
    var searchSubject: BehaviorSubject<String> = BehaviorSubject.create<String>()
    var items = ObservableArrayList<ImageEntity>()
    val itemBinding: ItemBinding<ImageEntity> =
        ItemBinding.of<ImageEntity>(
            BR.model,
            R.layout.image_list_item
        ).bindExtra(BR.listener, object : OnItemClickListener {
            override fun onItemClicked(imageEntity: ImageEntity?) {
                imageEntity?.let {
                    onItemClickEvent.value = imageEntity
                }
            }
        })

    init {
        searchString.value = DEFAULT_SEARCH_QUERY
    }

    fun search(query: String, isReload: Boolean = false) {
        updatePageNumber(query, isReload)
        imageRepository.searchImage(query, pageNumber).toFlowable().doOnSubscribe {
            requestInProgress = true
            allHitsLoaded = false
        }.doFinally {
            requestInProgress = false
        }.subscribe { result ->
            when (result) {
                is ResultState.Success -> {
                    allHitsLoaded = items.size == result.data.totalHits
                    searchSuccessEvent.value = result.data.hits
                }
                is ResultState.Error -> {
                    when (pageNumber) {
                        1 -> loadFromCache(query)
                        else -> pageNumber--
                    }
                }
            }
        }.track()
    }

    private fun loadFromCache(query: String) {
        try {
            viewModelScope.launch(Dispatchers.Main) {
                val imageList = withContext(Dispatchers.IO) {
                    imageRepository.getImagesFromCache(
                        query
                    )
                }
                items.clear()
                imageList.takeIf { it.isNotEmpty() }?.let {
                    allHitsLoaded = true
                    searchSuccessEvent.value = it
                } ?: searchFailEvent.call()
            }
        } catch (e: Exception) {
            searchFailEvent.call()
        }
    }

    fun setItems(hits: List<ImageEntity>) {
        if (pageNumber == 1)
            items.clear()
        items.addAll(hits)
    }

    private fun updatePageNumber(query: String, isReload: Boolean) {
        if (lastSearchedWord == query && !isReload) {
            pageNumber++
        } else {
            pageNumber = 1
        }
        lastSearchedWord = query
    }

    companion object {
        private const val DEFAULT_SEARCH_QUERY = "apple"
    }
}