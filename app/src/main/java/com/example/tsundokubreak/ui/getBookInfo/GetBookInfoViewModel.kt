package com.example.tsundokubreak.ui.getBookInfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tsundokubreak.domain.entity.bookInfo.TsundokuBook

class GetBookInfoViewModel : ViewModel() {
    var canRegisterBook: Boolean = false

    fun changeRegisterBookBool() {
        canRegisterBook = true
    }

    val gotBookInfo: MutableLiveData<TsundokuBook> =
        MutableLiveData(
            TsundokuBook(
                0,
                "???",
                "???",
                0,
                "",
            )
        )
}
