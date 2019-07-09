package dvachmovie.usecase.db

import dvachmovie.db.data.NullMovie
import dvachmovie.storage.MovieStorage
import dvachmovie.utils.MovieUtils
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MergeDBandCacheUseCaseTest {

    @InjectMocks
    lateinit var useCase: MergeDBandCacheUseCase

    @Mock
    lateinit var movieStorage: MovieStorage
    @Mock
    lateinit var movieUtils: MovieUtils

    @Test
    fun `Happy pass`() {
        val testList = listOf(NullMovie(), NullMovie("test"))
        val testTwoList = listOf(NullMovie("aaa"), NullMovie("bbb"))

        val resultList = testList + testTwoList
        given(movieStorage.movieList).willReturn(testList)
        given(movieUtils.calculateDiff(testList, testTwoList)).willReturn(testTwoList)
        Assert.assertEquals(resultList, useCase.execute(testTwoList))
    }
}
