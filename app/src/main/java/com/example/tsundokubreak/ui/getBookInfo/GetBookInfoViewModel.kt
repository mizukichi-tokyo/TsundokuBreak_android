package com.example.tsundokubreak.ui.getBookInfo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tsundokubreak.domain.entity.bookInfo.TsundokuBook

class GetBookInfoViewModel : ViewModel() {
    val testInfo: MutableLiveData<TsundokuBook> =
        MutableLiveData(
            TsundokuBook(
                0,
                "すごい本",
                "窪田瑞輝",
                100,
                "http://books.google.com/books/content?id=RhbBoAEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
            )
        )
}
