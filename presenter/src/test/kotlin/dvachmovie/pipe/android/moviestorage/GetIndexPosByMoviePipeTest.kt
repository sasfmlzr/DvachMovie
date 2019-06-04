package dvachmovie.pipe.android.moviestorage

import dvachmovie.db.data.Movie
import dvachmovie.usecase.moviestorage.GetIndexPosByMovieUseCase
import org.joda.time.LocalDateTime
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetIndexPosByMoviePipeTest {

    @InjectMocks
    lateinit var pipe: GetIndexPosByMoviePipe

    @Mock
    lateinit var useCase: GetIndexPosByMovieUseCase

    @Test
    fun `Happy pass`() {
        val movieOne = object : Movie {
            override val movieUrl: String
                get() = "testOne"
            override val previewUrl: String
                get() = "testOne"
            override val board: String
                get() = "testOne"
            override var isPlayed: Boolean
                get() = false
                set(value) {}
            override var date: LocalDateTime
                get() = LocalDateTime()
                set(value) {}
            override val md5: String
                get() = "testOne"
            override val post: Long
                get() = 0
            override val thread: Long
                get() = 0
        }

        given(useCase.execute(movieOne)).willReturn(999)
        Assert.assertEquals(999, pipe.execute(movieOne))
    }
}
