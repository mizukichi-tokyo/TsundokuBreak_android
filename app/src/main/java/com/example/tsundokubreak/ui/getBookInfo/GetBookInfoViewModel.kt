package com.example.tsundokubreak.ui.getBookInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tsundokubreak.domain.entity.bookInfo.TsundokuBook
import com.example.tsundokubreak.domain.repository.searchBookInfo.SearchBookInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GetBookInfoViewModel @Inject constructor(
    private val searchBookInfoRepository: SearchBookInfoRepository
) : ViewModel() {
    private var _canRegisterBook: MutableLiveData<Boolean> = MutableLiveData(false)
    var canRegisterBook: LiveData<Boolean> = _canRegisterBook

    fun changeRegisterBookBool() {
        _canRegisterBook = MutableLiveData(true)
    }


    private val _gotBookInfo: MutableLiveData<TsundokuBook> =
        MutableLiveData(
            TsundokuBook(
                0,
                "???",
                "???",
                0,
                "",
            )
        )
    val gotBookInfo:LiveData<TsundokuBook> = _gotBookInfo
}
