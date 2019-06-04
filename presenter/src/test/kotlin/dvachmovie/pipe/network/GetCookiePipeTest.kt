package dvachmovie.pipe.network

import dvachmovie.api.Cookie
import dvachmovie.usecase.real.GetCookieUseCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCookiePipeTest {

    @InjectMocks
    lateinit var pipe: GetCookiePipe

    @Mock
    lateinit var useCase: GetCookieUseCase

    @Test
    fun `Happy pass`() {
        val cookie = Cookie("test", "test")
        given(useCase.execute(Unit)).willReturn(cookie)
        Assert.assertEquals(cookie, pipe.execute(Unit))
    }
}
