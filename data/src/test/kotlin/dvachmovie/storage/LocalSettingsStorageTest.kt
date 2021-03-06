package dvachmovie.storage

import dvachmovie.AppConfig
import dvachmovie.TestException
import dvachmovie.api.DvachBoards
import dvachmovie.architecture.ScopeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.doReturn
import org.mockito.BDDMockito.doThrow
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocalSettingsStorageTest {

    @InjectMocks
    lateinit var settingsStorage: LocalSettingsStorage

    @Mock
    lateinit var keyValueStorage: KeyValueStorage

    @Mock
    lateinit var scopeProvider: ScopeProvider

    private val testException = TestException()

    companion object {
        private const val REPORT_BTN_VISIBLE = "ReportBtnVisibleOrNot"
        private const val LIST_BTN_VISIBLE = "ListBtnVisibleOrNot"
        private const val CURRENT_BASE_URL = "currentBaseUrl"
        private const val BOARD = "board"
        private const val COOKIE = "cookie"
        private const val GESTURE = "gesture"
        private const val COOKIE_DEFAULT_VALUE = "92ea293bf47456479e25b11ba67bb17a"
    }

    @Before
    fun `Set up`() {
        given(scopeProvider.ioScope).willReturn(CoroutineScope(Dispatchers.IO))
    }

    @Test
    fun `Put ReportBtnVisible to storage was successful`() {
        runBlocking {
            doNothing().`when`(keyValueStorage).putBoolean(REPORT_BTN_VISIBLE, true)

            settingsStorage.putReportBtnVisible(true).await()
        }
    }

    @Test(expected = TestException::class)
    fun `Put ReportBtnVisible to storage was fault`() {
        runBlocking {
            doThrow(testException).`when`(keyValueStorage).putBoolean(REPORT_BTN_VISIBLE, true)

            settingsStorage.putReportBtnVisible(true).await()
        }
    }

    @Test
    fun `Get ReportBtnVisible is successful`() {
        doReturn(false).`when`(keyValueStorage).getBoolean(REPORT_BTN_VISIBLE)
        Assert.assertEquals(false, settingsStorage.isReportBtnVisible())

        runBlocking {
            Assert.assertEquals(false, settingsStorage.isReportBtnVisibleAsync().await())
        }
    }

    @Test
    fun `Get ReportBtnVisible is fault, but return default value`() {
        doReturn(null).`when`(keyValueStorage).getBoolean(REPORT_BTN_VISIBLE)
        Assert.assertEquals(false, settingsStorage.isReportBtnVisible())

        runBlocking {
            Assert.assertEquals(false, settingsStorage.isReportBtnVisibleAsync().await())
        }
    }

    @Test
    fun `Put ListBtnVisible to storage was successful`() {
        runBlocking {
            doNothing().`when`(keyValueStorage).putBoolean(LIST_BTN_VISIBLE, true)

            settingsStorage.putListBtnVisible(true).await()
        }
    }

    @Test(expected = TestException::class)
    fun `Put ListBtnVisible to storage was fault`() {
        runBlocking {
            doThrow(testException).`when`(keyValueStorage).putBoolean(LIST_BTN_VISIBLE, true)

            settingsStorage.putListBtnVisible(true).await()
        }
    }

    @Test
    fun `Get ListBtnVisible is successful`() {
        doReturn(false).`when`(keyValueStorage).getBoolean(LIST_BTN_VISIBLE)
        Assert.assertEquals(false, settingsStorage.isListBtnVisible())

        runBlocking {
            Assert.assertEquals(false, settingsStorage.isListBtnVisibleAsync().await())
        }
    }

    @Test
    fun `Get ListBtnVisible is fault, but return default value`() {
        doReturn(null).`when`(keyValueStorage).getBoolean(LIST_BTN_VISIBLE)
        Assert.assertEquals(true, settingsStorage.isListBtnVisible())

        runBlocking {
            Assert.assertEquals(true, settingsStorage.isListBtnVisibleAsync().await())
        }
    }

    @Test
    fun `Put CurrentBaseUrl to storage was successful`() {
        runBlocking {
            doNothing().`when`(keyValueStorage).putString(CURRENT_BASE_URL, "test")

            settingsStorage.putCurrentBaseUrl("test").await()
        }
    }

    @Test(expected = TestException::class)
    fun `Put CurrentBaseUrl to storage was fault`() {
        runBlocking {
            doThrow(testException).`when`(keyValueStorage).putString(CURRENT_BASE_URL, "test")

            settingsStorage.putCurrentBaseUrl("test").await()
        }
    }

    @Test
    fun `Get CurrentBaseUrl is successful`() {
        doReturn("test").`when`(keyValueStorage).getString(CURRENT_BASE_URL)
        Assert.assertEquals("test", settingsStorage.getCurrentBaseUrl())

        runBlocking {
            doReturn("test").`when`(keyValueStorage).getString(CURRENT_BASE_URL)
            Assert.assertEquals("test", settingsStorage.getCurrentBaseUrlAsync().await())
        }
    }

    @Test
    fun `Get CurrentBaseUrl is fault, but return default value`() {
        doReturn(null).`when`(keyValueStorage).getString(CURRENT_BASE_URL)

        Assert.assertEquals(AppConfig.DVACH_URL, settingsStorage.getCurrentBaseUrl())

        runBlocking {
            Assert.assertEquals(AppConfig.DVACH_URL, settingsStorage.getCurrentBaseUrlAsync().await())
        }
    }

    @Test
    fun `Put Board to storage was successful`() {
        runBlocking {
            doNothing().`when`(keyValueStorage).putString(BOARD, "test")

            settingsStorage.putBoard("test").await()
        }
    }

    @Test(expected = TestException::class)
    fun `Put Board to storage was fault`() {
        runBlocking {
            doThrow(testException).`when`(keyValueStorage).putString(BOARD, "test")

            settingsStorage.putBoard("test").await()
        }
    }

    @Test
    fun `Get Board is successful`() {
        doReturn("test").`when`(keyValueStorage).getString(BOARD)
        Assert.assertEquals("test", settingsStorage.getBoard())

        runBlocking {
            doReturn("test").`when`(keyValueStorage).getString(BOARD)
            Assert.assertEquals("test", settingsStorage.getBoardAsync().await())
        }
    }

    @Test
    fun `Get Board is fault, but return default value`() {
        doReturn(null).`when`(keyValueStorage).getString(BOARD)
        Assert.assertEquals(DvachBoards.defaultMap.iterator().next().key,
                settingsStorage.getBoard())

        runBlocking {
            Assert.assertEquals(DvachBoards.defaultMap.iterator().next().key,
                    settingsStorage.getBoardAsync().await())
        }
    }

    @Test
    fun `Put Cookie to storage was successful`() {
        runBlocking {
            doNothing().`when`(keyValueStorage).putString(COOKIE, "test")

            settingsStorage.putCookie("test").await()
        }
    }

    @Test(expected = TestException::class)
    fun `Put Cookie to storage was fault`() {
        runBlocking {
            doThrow(testException).`when`(keyValueStorage).putString(COOKIE, "test")

            settingsStorage.putCookie("test").await()
        }
    }

    @Test
    fun `Get Cookie is successful`() {
        doReturn("test").`when`(keyValueStorage).getString(COOKIE)
        Assert.assertEquals("test", settingsStorage.getCookie())

        runBlocking {
            Assert.assertEquals("test", settingsStorage.getCookieAsync().await())
        }
    }

    @Test
    fun `Get Cookie is fault, but return default value`() {
        doReturn(null).`when`(keyValueStorage).getString(COOKIE)
        Assert.assertEquals(COOKIE_DEFAULT_VALUE, settingsStorage.getCookie())

        runBlocking {
            Assert.assertEquals(COOKIE_DEFAULT_VALUE, settingsStorage.getCookieAsync().await())
        }
    }

    @Test
    fun `Put AllowGesture to storage was successful`() {
        runBlocking {
            doNothing().`when`(keyValueStorage).putBoolean(GESTURE, true)

            settingsStorage.putIsAllowGesture(true).await()
        }
    }

    @Test(expected = TestException::class)
    fun `Put AllowGesture to storage was fault`() {
        runBlocking {
            doThrow(testException).`when`(keyValueStorage).putBoolean(GESTURE, true)

            settingsStorage.putIsAllowGesture(true).await()
        }
    }

    @Test
    fun `Get AllowGesture is successful`() {
        doReturn(false).`when`(keyValueStorage).getBoolean(GESTURE)
        Assert.assertEquals(false, settingsStorage.isAllowGesture())

        runBlocking {
            Assert.assertEquals(false, settingsStorage.isAllowGestureAsync().await())
        }
    }

    @Test
    fun `Get AllowGesture is fault, but return default value`() {
        doReturn(null).`when`(keyValueStorage).getBoolean(GESTURE)
        Assert.assertEquals(false, settingsStorage.isAllowGesture())

        runBlocking {
            Assert.assertEquals(false, settingsStorage.isAllowGestureAsync().await())
        }
    }
}
