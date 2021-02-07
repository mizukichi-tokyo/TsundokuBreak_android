package com.example.tsundokubreak.domain.entity.bookInfo

data class BookInfo(
    val kind: String? = null,
    val totalItems: Long? = null,
    val items: List<Item>? = null
)

data class Item(
    val kind: String? = null,
    val id: String? = null,
    val etag: String? = null,
    val selfLink: String? = null,
    val volumeInfo: VolumeInfo? = null,
    val saleInfo: SaleInfo? = null,
    val accessInfo: AccessInfo? = null,
    val searchInfo: SearchInfo? = null
)

data class AccessInfo(
    val country: String? = null,
    val viewability: String? = null,
    val embeddable: Boolean? = null,
    val publicDomain: Boolean? = null,
    val textToSpeechPermission: String? = null,
    val epub: Epub? = null,
    val pdf: Epub? = null,
    val webReaderLink: String? = null,
    val accessViewStatus: String? = null,
    val quoteSharingAllowed: Boolean? = null
)

data class Epub(
    val isAvailable: Boolean? = null
)

data class SaleInfo(
    val country: String? = null,
    val saleability: String? = null,
    val isEbook: Boolean? = null
)

data class SearchInfo(
    val textSnippet: String? = null
)

data class VolumeInfo(
    val title: String? = null,
    val subtitle: String? = null,
    val authors: List<String>? = null,
    val publishedDate: String? = null,
    val description: String? = null,
    val industryIdentifiers: List<IndustryIdentifier>? = null,
    val readingModes: ReadingModes? = null,
    val pageCount: Long? = null,
    val printType: String? = null,
    val maturityRating: String? = null,
    val allowAnonLogging: Boolean? = null,
    val contentVersion: String? = null,
    val imageLinks: ImageLinks? = null,
    val language: String? = null,
    val previewLink: String? = null,
    val infoLink: String? = null,
    val canonicalVolumeLink: String? = null
)

data class ImageLinks(
    val smallThumbnail: String? = null,
    val thumbnail: String? = null
)

data class IndustryIdentifier(
    val type: String? = null,
    val identifier: String? = null
)

data class ReadingModes(
    val text: Boolean? = null,
    val image: Boolean? = null
)
