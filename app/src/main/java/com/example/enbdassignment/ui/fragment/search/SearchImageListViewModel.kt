package com.example.enbdassignment.ui.fragment.search

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.enbdassignment.BR
import com.example.enbdassignment.R
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
import timber.log.Timber
import javax.inject.Inject

class SearchImageListViewModel @Inject constructor(private val imageRepository: ImageRepository) :
    BaseViewModel() {
    var allHitsLoaded: Boolean = false
    var requestInProgress: Boolean = false
    private var pageNumber = 1
    var lastSearchedWord: String? = null
    var searchString = MutableLiveData<String>()
    var searchSubject: BehaviorSubject<String> = BehaviorSubject.create<String>()
    var items = ObservableArrayList<ImageEntity>()
    val itemBinding: ItemBinding<ImageEntity> =
        ItemBinding.of<ImageEntity>(
            BR.model,
            R.layout.image_list_item
        ).bindExtra(BR.listener, object : OnItemClickListener {
            override fun onItemClicked(imageEntity: ImageEntity?) {

            }
        })

    init {
        searchString.value = DEFAULT_SEARCH_QUERY
    }

    fun search(query: String) {
        if (requestInProgress || allHitsLoaded)
            return
        imageRepository.searchImage(query, pageNumber).toFlowable().subscribe { result ->
            when (result) {
                is ResultState.Success -> {
                    items.addAll(result.data.hits)
                    allHitsLoaded = items.size == result.data.totalHits
                    viewModelScope.launch(Dispatchers.IO) {
                        imageRepository.storeImageListCache(result.data.hits)
                    }
                }
                is ResultState.Error -> {
                    lastSearchedWord = null
                    try {
                        viewModelScope.launch(Dispatchers.Main) {
                            val imageList = withContext(Dispatchers.IO) {
                                imageRepository.getImagesFromCache(
                                    query
                                )
                            }
                            items.addAll(imageList)
                        }
                    } catch (e: Exception) {
                        Timber.e(e)
                    }
                }
            }
            requestInProgress = false
        }.track()
    }

    companion object {
        private const val DEFAULT_SEARCH_QUERY = "apple"
    }
}