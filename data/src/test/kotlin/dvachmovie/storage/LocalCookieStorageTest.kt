package dvachmovie.storage

import dvachmovie.TestException
import dvachmovie.api.Cookie
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocalCookieStorageTest {

    @InjectMocks
    lateinit var localCookieStorage: LocalCookieStorage

    @Mock
    lateinit var settingsStorage: SettingsStorage

    private val header = "usercode_auth"

    @Test
    fun `Happy pass`() {
        runBlocking {
            val testCookie = Cookie(header, "test")
            given(settingsStorage.getCookie()).willReturn("test")

            Assert.assertEquals(testCookie, localCookieStorage.cookie)
            Assert.assertEquals(testCookie.toString(), localCookieStorage.cookie.toString())
        }
    }

    @Test (expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(settingsStorage.getCookie()).willThrow(TestException())

            localCookieStorage.cookie
        }
    }
}
