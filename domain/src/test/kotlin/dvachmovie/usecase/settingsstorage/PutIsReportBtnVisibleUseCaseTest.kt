package dvachmovie.usecase.settingsstorage

import dvachmovie.TestException
import dvachmovie.storage.SettingsStorage
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PutIsReportBtnVisibleUseCaseTest {

    @InjectMocks
    lateinit var useCase: PutIsReportBtnVisibleUseCase

    @Mock
    lateinit var settingsStorage: SettingsStorage

    @Test
    fun `Happy pass`() {
        runBlockingTest {
            given(settingsStorage.putReportBtnVisible(false)).willReturn(CompletableDeferred(Unit))
            Assert.assertEquals(Unit, useCase.executeAsync(false))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlockingTest {
            given(settingsStorage.putReportBtnVisible(false)).willThrow(TestException())
            useCase.executeAsync(false)
        }
    }
}
