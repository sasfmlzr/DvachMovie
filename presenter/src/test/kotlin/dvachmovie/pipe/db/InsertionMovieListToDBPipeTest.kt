package dvachmovie.pipe.db

import dvachmovie.db.data.NullMovie
import dvachmovie.usecase.db.InsertionMovieListToDBUseCase
import kotlinx.coroutines.runBlocking
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

    private val movieOne = NullMovie("testOne")

    private val movieTwo = NullMovie("testTwo")

    private val testList = listOf(movieOne, movieTwo)

    @Test
    fun `Happy pass`() {
        runBlocking {
            pipe.execute(testList)
        }
    }
}
