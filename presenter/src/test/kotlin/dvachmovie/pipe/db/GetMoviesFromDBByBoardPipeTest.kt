package dvachmovie.pipe.db

import dvachmovie.db.data.NullMovie
import dvachmovie.usecase.db.GetMoviesFromDBByBoardUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMoviesFromDBByBoardPipeTest {

    @InjectMocks
    lateinit var pipe: GetMoviesFromDBByBoardPipe

    @Mock
    lateinit var useCase: GetMoviesFromDBByBoardUseCase

    @Test
    fun `Happy pass`() {
        val resultList = listOf(NullMovie("aaa"), NullMovie("bbb"))
        val testValue = Pair("testOne", "testTwo")
        runBlocking {
            given(useCase.executeAsync(testValue)).willReturn(resultList)
            Assert.assertEquals(resultList, pipe.execute(testValue))
        }
    }
}
