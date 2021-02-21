package com.mizukikubota.tsundokubreak.domain.entity.bookInfo

import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class APIBookInfoResponseTest() {
    @Test
    fun googleAPIBookInfoResponseTest() {
        // given
        val testAPIResponse = mockk<APIBookInfoResponse> {
            every { kind } returns "books#volumes"
            every { totalItems } returns 1
            every { items } returns listOf(
                mockk {
                    every { volumeInfo } returns
                            mockk {
                                every { title } returns "マイクロインタラクション"
                                every { authors } returns listOf(
                                    "Dan Saffer"
                                )
                                every { pageCount } returns 217
                                every { imageLinks } returns
                                        mockk {
                                            every { smallThumbnail } returns "http://books.google.com/books/content?id=RhbBoAEACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api"
                                            every { thumbnail } returns "http://books.google.com/books/content?id=RhbBoAEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api"
                                        }
                            }
                }
            )
        }

        // when
        val kind = testAPIResponse.kind
        val totalItems = testAPIResponse.totalItems
        val title = testAPIResponse.items?.first()?.volumeInfo?.title
        val author = testAPIResponse.items?.first()?.volumeInfo?.authors?.first()
        val pageCount = testAPIResponse.items?.first()?.volumeInfo?.pageCount
        val smallThumbnail = testAPIResponse.items?.first()?.volumeInfo?.imageLinks?.smallThumbnail
        val thumbnail = testAPIResponse.items?.first()?.volumeInfo?.imageLinks?.thumbnail

        // then
        assertThat(kind).isEqualTo("books#volumes")
        assertThat(totalItems).isEqualTo(1)
        assertThat(title).isEqualTo("マイクロインタラクション")
        assertThat(author).isEqualTo("Dan Saffer")
        assertThat(pageCount).isEqualTo(217)
        assertThat(smallThumbnail).isEqualTo("http://books.google.com/books/content?id=RhbBoAEACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api")
        assertThat(thumbnail).isEqualTo("http://books.google.com/books/content?id=RhbBoAEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api")
    }
}
