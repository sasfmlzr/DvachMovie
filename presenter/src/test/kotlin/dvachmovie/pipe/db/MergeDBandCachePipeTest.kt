package dvachmovie.pipe.db

import dvachmovie.db.data.NullMovie
import dvachmovie.usecase.db.MergeDBandCacheUseCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MergeDBandCachePipeTest {

    @InjectMocks
    lateinit var pipe: MergeDBandCachePipe

    @Mock
    lateinit var useCase: MergeDBandCacheUseCase

    @Test
    fun `Happy pass`() {
        val testList = listOf(NullMovie(), NullMovie("test"))
        val resultList = listOf(NullMovie("aaa"), NullMovie("bbb"))
        given(useCase.execute(testList)).willReturn(resultList)
        Assert.assertEquals(resultList, pipe.execute(testList))
    }
}
