package dvachmovie.pipe.utils

import dvachmovie.PresenterModel
import dvachmovie.db.data.NullMovie
import dvachmovie.usecase.utils.ShuffleMoviesUseCase
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ShuffleMoviesPipeTest {

    @InjectMocks
    lateinit var pipe: ShuffleMoviesPipe

    @Mock
    lateinit var useCase: ShuffleMoviesUseCase

    @Mock
    lateinit var broadcastChannel: BroadcastChannel<PresenterModel>

    private val movieOne = NullMovie("testOne")

    private val movieTwo = NullMovie("testTwo")

    private val testList = listOf(movieOne, movieTwo)
    private val resultList = listOf(movieTwo, movieOne)

    @Test
    fun `Happy pass`() {
        runBlocking {
            given(useCase.executeAsync(testList)).willReturn(resultList)
            pipe.execute(testList)
        }
    }
}
