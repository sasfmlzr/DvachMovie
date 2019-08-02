package dvachmovie.pipe.db

import dvachmovie.db.data.NullMovie
import dvachmovie.usecase.db.InsertionMovieToDBUseCase
import kotlinx.coroutines.runBlocking
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

    private val movieOne = NullMovie("testOne")

    @Test
    fun `Happy pass`() {
        runBlocking {
            pipe.execute(movieOne)
        }
    }
}
