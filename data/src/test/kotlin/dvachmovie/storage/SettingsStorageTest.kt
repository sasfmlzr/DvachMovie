package dvachmovie.storage

import dvachmovie.TestException
import dvachmovie.api.model.Boards
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
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
        runBlocking {
            doNothing().`when`(keyValueStorage).putBoolean(LOADING_PARAM, true)

            settingsStorage.putLoadingEveryTime(true).await()
        }
    }

    @Test(expected = TestException::class)
    fun `Put LoadingEveryTime to storage was fault`() {
        runBlocking {
            doThrow(testException).`when`(keyValueStorage).putBoolean(LOADING_PARAM, true)

            settingsStorage.putLoadingEveryTime(true).await()
        }
    }

    @Test
    fun `Get LoadingEveryTime is successful`() {
        runBlocking {
            doReturn(true).`when`(keyValueStorage).getBoolean(LOADING_PARAM)

            Assert.assertEquals(true, settingsStorage.isLoadingEveryTime().await())
        }
    }

    @Test
    fun `Get LoadingEveryTime is fault, but return default value`() {
        runBlocking {
            doReturn(null).`when`(keyValueStorage).getBoolean(LOADING_PARAM)

            Assert.assertEquals(false, settingsStorage.isLoadingEveryTime().await())
        }
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
        runBlocking {
            doReturn(false).`when`(keyValueStorage).getBoolean(REPORT_BTN_VISIBLE)

            Assert.assertEquals(false, settingsStorage.isReportBtnVisible().await())
        }
    }

    @Test
    fun `Get ReportBtnVisible is fault, but return default value`() {
        runBlocking {
            doReturn(null).`when`(keyValueStorage).getBoolean(REPORT_BTN_VISIBLE)

            Assert.assertEquals(true, settingsStorage.isReportBtnVisible().await())
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
        runBlocking {
            doReturn(false).`when`(keyValueStorage).getBoolean(LIST_BTN_VISIBLE)

            Assert.assertEquals(false, settingsStorage.isListBtnVisible().await())
        }
    }

    @Test
    fun `Get ListBtnVisible is fault, but return default value`() {
        runBlocking {
            doReturn(null).`when`(keyValueStorage).getBoolean(LIST_BTN_VISIBLE)

            Assert.assertEquals(true, settingsStorage.isListBtnVisible().await())
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
        runBlocking {
            doReturn("test").`when`(keyValueStorage).getString(BOARD)

            Assert.assertEquals("test", settingsStorage.getBoard().await())
        }
    }

    @Test
    fun `Get Board is fault, but return default value`() {
        runBlocking {
            doReturn(null).`when`(keyValueStorage).getString(BOARD)

            Assert.assertEquals(Boards.defaultMap.iterator().next().key,
                    settingsStorage.getBoard().await())
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
        runBlocking {
            doReturn("test").`when`(keyValueStorage).getString(COOKIE)

            Assert.assertEquals("test", settingsStorage.getCookie().await())
        }
    }

    @Test
    fun `Get Cookie is fault, but return default value`() {
        runBlocking {
            doReturn(null).`when`(keyValueStorage).getString(COOKIE)

            Assert.assertEquals("", settingsStorage.getCookie().await())
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
        runBlocking {
            doReturn(false).`when`(keyValueStorage).getBoolean(GESTURE)

            Assert.assertEquals(false, settingsStorage.isAllowGesture().await())
        }
    }

    @Test
    fun `Get AllowGesture is fault, but return default value`() {
        runBlocking {
            doReturn(null).`when`(keyValueStorage).getBoolean(GESTURE)

            Assert.assertEquals(true, settingsStorage.isAllowGesture().await())
        }
    }
}
