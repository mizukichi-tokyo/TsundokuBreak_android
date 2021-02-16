package com.mizukikubota.tsundokubreak.domain.entity.common

sealed class Resource<out T : Any?> {
    data class Success<out T : Any?>(val data: T?) : Resource<T>()
    data class NetworkError(val exception: Throwable) : Resource<Nothing>()
    data class ApiError(val errorBody: ErrorBody) : Resource<Nothing>()
    object InProgress : Resource<Nothing>()
    object Empty : Resource<Nothing>()

    val extractData: T?
        get() = when (this) {
            is Success -> data
            is NetworkError -> null
            is ApiError -> null
            is InProgress -> null
            is Empty -> null
        }
}
