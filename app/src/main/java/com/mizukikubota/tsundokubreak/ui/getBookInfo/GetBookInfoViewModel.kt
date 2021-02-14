package com.mizukikubota.tsundokubreak.ui.getBookInfo

import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mizukikubota.tsundokubreak.domain.entity.bookInfo.TsundokuBook
import com.mizukikubota.tsundokubreak.domain.entity.common.Resource
import com.mizukikubota.tsundokubreak.domain.repository.searchBookInfo.SearchBookInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.Timber.d
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
                    if (it =="") {
                        searchBookInfoRepository.getBookInfo(null).collect {
                            _gotBookInfo.value = it
                        }
                    } else {
                        searchBookInfoRepository.getBookInfo(it).collect {
                            _gotBookInfo.value = it
                        }
                    }
                }
            }
        }

    private val _canRegisterBook = MutableLiveData(false)
    val canRegisterBook: LiveData<Boolean> = _canRegisterBook
    fun setRegisterBookState(registerState: Boolean) {
        _canRegisterBook.value = registerState
    }

    fun clickRegister(
        callback: () -> Unit,
        fallback: () -> Unit
    ) {
        gotBookInfo.value.extractData?.let {
            viewModelScope.launch {
                searchBookInfoRepository.addBookToTsundoku(it, callback) {
                    fallback()
                }
            }
        }
    }

    val emptyBookInfo =
        TsundokuBook(
        0,
        "該当する書籍がありません",
        "ISBNコードを入力してください",
        0,
        "",)
}
