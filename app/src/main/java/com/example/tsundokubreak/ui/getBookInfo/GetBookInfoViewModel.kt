package com.example.tsundokubreak.ui.getBookInfo

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsundokubreak.domain.entity.bookInfo.TsundokuBook
import com.example.tsundokubreak.domain.entity.common.Resource
import com.example.tsundokubreak.domain.repository.searchBookInfo.SearchBookInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GetBookInfoViewModel @Inject constructor(
    private val searchBookInfoRepository: SearchBookInfoRepository
) : ViewModel() {

    private val _gotBookInfo = MutableStateFlow<Resource<TsundokuBook>>(Resource.Empty)

    val gotBookInfo: StateFlow<Resource<TsundokuBook>> = _gotBookInfo

    private val _isbnString = MutableLiveData("0")

    fun setIsbnString(isbn: String) {
        _isbnString.value = isbn
    }

    fun getBookInfoFromISBN() {
        viewModelScope.launch {
            _isbnString.value?.let {
                searchBookInfoRepository.getBookInfo(it).collect {
                    _gotBookInfo.value = it
                    Timber.d(it.toString())
                    }
                }
            }
        }




    private var _canRegisterBook: MutableLiveData<Boolean> = MutableLiveData(false)
    var canRegisterBook: LiveData<Boolean> = _canRegisterBook

    fun changeRegisterBookBool() {
        _canRegisterBook = MutableLiveData(true)
    }


    val emptyBookInfo =
        TsundokuBook(
        0,
        "???",
        "???",
        0,
        "",)
}
