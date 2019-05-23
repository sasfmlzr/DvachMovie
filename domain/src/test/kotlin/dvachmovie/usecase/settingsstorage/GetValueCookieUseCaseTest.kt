package dvachmovie.usecase.settingsstorage

import dvachmovie.TestException
import dvachmovie.storage.SettingsStorage
import dvachmovie.usecase.settingsStorage.GetValueCookieUseCase
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetValueCookieUseCaseTest {

    @InjectMocks
    lateinit var useCaseValue: GetValueCookieUseCase

    @Mock
    lateinit var settingsStorage: SettingsStorage

    @Test
    fun `Happy pass`() {
        runBlocking {
            given(settingsStorage.getCookie()).willReturn(CompletableDeferred("test"))
            Assert.assertEquals("test", useCaseValue.execute(Unit))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(settingsStorage.getCookie()).willThrow(TestException())
            useCaseValue.execute(Unit)
        }
    }
}