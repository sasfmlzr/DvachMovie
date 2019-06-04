package dvachmovie.pipe.settingsstorage

import dvachmovie.usecase.settingsstorage.GetBoardUseCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetBoardPipeTest {

    @InjectMocks
    lateinit var pipe: GetBoardPipe

    @Mock
    lateinit var useCase: GetBoardUseCase

    @Test
    fun `Happy pass`() {
        given(useCase.execute(Unit)).willReturn("test")
        Assert.assertEquals("test", pipe.execute(Unit))
    }
}
