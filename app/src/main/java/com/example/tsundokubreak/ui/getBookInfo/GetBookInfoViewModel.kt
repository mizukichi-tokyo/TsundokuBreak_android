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

    var isbnString = MutableLiveData<String?>()

    fun getBookInfoFromISBN() {
        viewModelScope.launch {
            isbnString.value?.let {
                searchBookInfoRepository.getBookInfo(it).collect {
                    _gotBookInfo.value = it
                    }
                }
            }
        }

    private val _canRegisterBook = MutableLiveData(false)
    val canRegisterBook: LiveData<Boolean> = _canRegisterBook
    fun setRegisterBookState(registerState: Boolean) {
        _canRegisterBook.value = registerState
    }

    val emptyBookInfo =
        TsundokuBook(
        0,
        "該当する書籍がありません",
        "ISBNコードを入力してください",
        0,
        "",)
}
