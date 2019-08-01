package dvachmovie.pipe.settingsstorage

import dvachmovie.usecase.settingsstorage.GetCurrentBaseUrlUseCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCurrentBaseUrlPipeTest {

    @InjectMocks
    lateinit var pipe: GetCurrentBaseUrlPipe

    @Mock
    lateinit var useCase: GetCurrentBaseUrlUseCase

    @Test
    fun `Happy pass`() {
        given(useCase.execute(Unit)).willReturn("test")
        Assert.assertEquals("test", pipe.execute(Unit))
    }
}
