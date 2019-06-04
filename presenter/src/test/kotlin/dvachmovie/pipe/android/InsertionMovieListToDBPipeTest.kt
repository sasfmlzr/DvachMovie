package dvachmovie.pipe.android

import dvachmovie.db.data.Movie
import dvachmovie.usecase.InsertionMovieListToDBUseCase
import org.joda.time.LocalDateTime
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InsertionMovieListToDBPipeTest {

    @InjectMocks
    lateinit var pipe: InsertionMovieListToDBPipe

    @Mock
    lateinit var useCase: InsertionMovieListToDBUseCase

    private val movieOne = object : Movie {
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

    private val movieTwo = object : Movie {
        override val movieUrl: String
            get() = "testTwo"
        override val previewUrl: String
            get() = "testTwo"
        override val board: String
            get() = "testTwo"
        override var isPlayed: Boolean
            get() = false
            set(value) {}
        override var date: LocalDateTime
            get() = LocalDateTime()
            set(value) {}
        override val md5: String
            get() = "testTwo"
        override val post: Long
            get() = 0
        override val thread: Long
            get() = 0
    }

    private val testList = listOf(movieOne, movieTwo)

    @Test
    fun `Happy pass`() {
        pipe.execute(testList)
    }
}
