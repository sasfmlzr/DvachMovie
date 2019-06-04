package dvachmovie.pipe.settingsstorage

import dvachmovie.usecase.settingsstorage.GetValueCookieUseCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetValueCookiePipeTest {

    @InjectMocks
    lateinit var pipe: GetValueCookiePipe

    @Mock
    lateinit var useCase: GetValueCookieUseCase

    @Test
    fun `Happy pass`() {
        given(useCase.execute(Unit)).willReturn("test")
        Assert.assertEquals("test", pipe.execute(Unit))
    }
}
