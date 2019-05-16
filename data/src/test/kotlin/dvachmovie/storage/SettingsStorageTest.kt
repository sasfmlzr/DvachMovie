package dvachmovie.storage

import dvachmovie.TestException
import dvachmovie.api.model.Boards
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.doReturn
import org.mockito.BDDMockito.doThrow
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SettingsStorageTest {

    @InjectMocks
    lateinit var settingsStorage: SettingsStorage

    @Mock
    lateinit var keyValueStorage: KeyValueStorage

    private val testException = TestException()

    companion object {
        private const val LOADING_PARAM = "LoadingMoviesOrNot"
        private const val REPORT_BTN_VISIBLE = "ReportBtnVisibleOrNot"
        private const val LIST_BTN_VISIBLE = "ListBtnVisibleOrNot"
        private const val BOARD = "board"
        private const val COOKIE = "cookie"
        private const val GESTURE = "gesture"
    }

    @Test
    fun `Put LoadingEveryTime to storage was successful`() {
        doNothing().`when`(keyValueStorage).putBoolean(LOADING_PARAM, true)
        runBlocking {
            settingsStorage.putLoadingEveryTime(true)
        }
    }

    @Test(expected = TestException::class)
    fun `Put LoadingEveryTime to storage was fault`() {
        doThrow(testException).`when`(keyValueStorage).putBoolean(LOADING_PARAM, true)
        runBlocking {
            settingsStorage.putLoadingEveryTime(true)
        }
    }

    @Test
    fun `Get LoadingEveryTime is successful`() {
        doReturn(true).`when`(keyValueStorage).getBoolean(LOADING_PARAM)
        Assert.assertEquals(true, settingsStorage.isLoadingEveryTime())
    }

    @Test
    fun `Get LoadingEveryTime is fault, but return default value`() {
        doReturn(null).`when`(keyValueStorage).getBoolean(LOADING_PARAM)
        Assert.assertEquals(false, settingsStorage.isLoadingEveryTime())
    }

    @Test
    fun `Put ReportBtnVisible to storage was successful`() {
        doNothing().`when`(keyValueStorage).putBoolean(REPORT_BTN_VISIBLE, true)
        runBlocking {
            settingsStorage.putReportBtnVisible(true)
        }
    }

    @Test(expected = TestException::class)
    fun `Put ReportBtnVisible to storage was fault`() {
        doThrow(testException).`when`(keyValueStorage).putBoolean(REPORT_BTN_VISIBLE, true)
        runBlocking {
            settingsStorage.putReportBtnVisible(true)
        }
    }

    @Test
    fun `Get ReportBtnVisible is successful`() {
        doReturn(false).`when`(keyValueStorage).getBoolean(REPORT_BTN_VISIBLE)
        Assert.assertEquals(false, settingsStorage.isReportBtnVisible())
    }

    @Test
    fun `Get ReportBtnVisible is fault, but return default value`() {
        doReturn(null).`when`(keyValueStorage).getBoolean(REPORT_BTN_VISIBLE)
        Assert.assertEquals(true, settingsStorage.isReportBtnVisible())
    }

    @Test
    fun `Put ListBtnVisible to storage was successful`() {
        doNothing().`when`(keyValueStorage).putBoolean(LIST_BTN_VISIBLE, true)
        runBlocking {
            settingsStorage.putListBtnVisible(true)
        }
    }

    @Test(expected = TestException::class)
    fun `Put ListBtnVisible to storage was fault`() {
        doThrow(testException).`when`(keyValueStorage).putBoolean(LIST_BTN_VISIBLE, true)
        runBlocking {
            settingsStorage.putListBtnVisible(true)
        }
    }

    @Test
    fun `Get ListBtnVisible is successful`() {
        doReturn(false).`when`(keyValueStorage).getBoolean(LIST_BTN_VISIBLE)
        Assert.assertEquals(false, settingsStorage.isListBtnVisible())
    }

    @Test
    fun `Get ListBtnVisible is fault, but return default value`() {
        doReturn(null).`when`(keyValueStorage).getBoolean(LIST_BTN_VISIBLE)
        Assert.assertEquals(true, settingsStorage.isListBtnVisible())
    }

    @Test
    fun `Put Board to storage was successful`() {
        doNothing().`when`(keyValueStorage).putString(BOARD, "test")
        runBlocking {
            settingsStorage.putBoard("test")
        }
    }

    @Test(expected = TestException::class)
    fun `Put Board to storage was fault`() {
        doThrow(testException).`when`(keyValueStorage).putString(BOARD, "test")
        runBlocking {
            settingsStorage.putBoard("test")
        }
    }

    @Test
    fun `Get Board is successful`() {
        doReturn("test").`when`(keyValueStorage).getString(BOARD)
        Assert.assertEquals("test", settingsStorage.getBoard())
    }

    @Test
    fun `Get Board is fault, but return default value`() {
        doReturn(null).`when`(keyValueStorage).getString(BOARD)
        Assert.assertEquals(Boards.defaultMap.iterator().next().key, settingsStorage.getBoard())
    }

    @Test
    fun `Put Cookie to storage was successful`() {
        doNothing().`when`(keyValueStorage).putString(COOKIE, "test")
        runBlocking {
            settingsStorage.putCookie("test")
        }
    }

    @Test(expected = TestException::class)
    fun `Put Cookie to storage was fault`() {
        doThrow(testException).`when`(keyValueStorage).putString(COOKIE, "test")
        runBlocking {
            settingsStorage.putCookie("test")
        }
    }

    @Test
    fun `Get Cookie is successful`() {
        doReturn("test").`when`(keyValueStorage).getString(COOKIE)
        Assert.assertEquals("test", settingsStorage.getCookie())
    }

    @Test
    fun `Get Cookie is fault, but return default value`() {
        doReturn(null).`when`(keyValueStorage).getString(COOKIE)
        Assert.assertEquals("", settingsStorage.getCookie())
    }

    @Test
    fun `Put AllowGesture to storage was successful`() {
        doNothing().`when`(keyValueStorage).putBoolean(GESTURE, true)
        runBlocking {
            settingsStorage.putIsAllowGesture(true)
        }
    }

    @Test(expected = TestException::class)
    fun `Put AllowGesture to storage was fault`() {
        doThrow(testException).`when`(keyValueStorage).putBoolean(GESTURE, true)
        runBlocking {
            settingsStorage.putIsAllowGesture(true)
        }
    }

    @Test
    fun `Get AllowGesture is successful`() {
        doReturn(false).`when`(keyValueStorage).getBoolean(GESTURE)
        Assert.assertEquals(false, settingsStorage.isAllowGesture())
    }

    @Test
    fun `Get AllowGesture is fault, but return default value`() {
        doReturn(null).`when`(keyValueStorage).getBoolean(GESTURE)
        Assert.assertEquals(true, settingsStorage.isAllowGesture())
    }
}
