package dvachmovie.usecase.real

import dvachmovie.TestException
import dvachmovie.api.Cookie
import dvachmovie.api.CookieStorage
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCookieUseCaseTest {
    @InjectMocks
    lateinit var getCookieUseCase: GetCookieUseCase

    @Mock
    lateinit var cookieStorage: CookieStorage

    private val testCookie = Cookie("test", "test")

    @Test
    fun `Happy pass`() {
        runBlocking {
            given(cookieStorage.cookie).willReturn(testCookie)

            Assert.assertEquals(testCookie, getCookieUseCase.execute(Unit))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(cookieStorage.cookie).willThrow(TestException())

            getCookieUseCase.execute(Unit)
        }
    }
}
