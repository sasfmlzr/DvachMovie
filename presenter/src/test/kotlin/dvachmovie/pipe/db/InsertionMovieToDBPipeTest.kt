package dvachmovie.pipe.db

import dvachmovie.db.data.Movie
import dvachmovie.usecase.db.InsertionMovieToDBUseCase
import kotlinx.coroutines.runBlocking
import org.joda.time.LocalDateTime
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InsertionMovieToDBPipeTest {

    @InjectMocks
    lateinit var pipe: InsertionMovieToDBPipe

    @Mock
    lateinit var useCase: InsertionMovieToDBUseCase

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

    @Test
    fun `Happy pass`() {
        runBlocking {
            pipe.execute(movieOne)
        }
    }
}
