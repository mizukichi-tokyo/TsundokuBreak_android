package com.example.tsundokubreak.ui.dokuryo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsundokubreak.domain.entity.bookInfo.TsundokuBook
import com.example.tsundokubreak.domain.repository.bookList.BookListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class DokuryoViewModel @Inject constructor(
    private val bookListRepository: BookListRepository
) : ViewModel() {

    private val _dokuryoBookList: MutableStateFlow<List<TsundokuBook>> = MutableStateFlow(emptyList())
    val dokuryoBookList: StateFlow<List<TsundokuBook>> = _dokuryoBookList

    init {
        viewModelScope.launch {
            bookListRepository.getAllDokuryoBook().collect {
                _dokuryoBookList.value = it
            }
        }
    }
}
