package com.mizukikubota.tsundokubreak.ui.tsundoku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mizukikubota.tsundokubreak.domain.entity.bookInfo.TsundokuBook
import com.mizukikubota.tsundokubreak.domain.repository.bookList.BookListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class TsundokuViewModel @Inject constructor(
    private val bookListRepository: BookListRepository
) : ViewModel() {

    private val _tsundokuBookList: MutableStateFlow<List<TsundokuBook>> = MutableStateFlow(emptyList())
    val tsundokuBookList: StateFlow<List<TsundokuBook>> = _tsundokuBookList

    init {
        viewModelScope.launch {
            bookListRepository.getAllTsundokuBook().collect {
                _tsundokuBookList.value = it
            }
        }
    }

    fun tsundokuToDokuryo(tsundokuBook: TsundokuBook) {
        tsundokuBook.dokuryo = true
        viewModelScope.launch {
            bookListRepository.updateTsundokuBook(tsundokuBook)
        }
    }

    fun tsundokuSetReadPageCount(tsundokuBook: TsundokuBook, readPageCount: Int) {
        tsundokuBook.readPageCount = readPageCount
        viewModelScope.launch {
            bookListRepository.updateTsundokuBook(tsundokuBook)
        }
    }
}
