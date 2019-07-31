package dvachmovie.usecase.settingsstorage

import dvachmovie.TestException
import dvachmovie.storage.SettingsStorage
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GetCurrentBaseUrlUseCaseTest {

    @InjectMocks
    lateinit var useCase: GetCurrentBaseUrlUseCase

    @Mock
    lateinit var settingsStorage: SettingsStorage

    @Test
    fun `Happy pass`() {
        given(settingsStorage.getCurrentBaseUrl()).willReturn("testBaseUrl")
        Assert.assertEquals("testBaseUrl", useCase.execute(Unit))
        runBlocking {
            given(settingsStorage.getCurrentBaseUrlAsync()).willReturn(CompletableDeferred("testBaseUrl"))

            Assert.assertEquals("testBaseUrl", useCase.executeAsync(Unit))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(settingsStorage.getCurrentBaseUrlAsync()).willThrow(TestException())
            useCase.executeAsync(Unit)
        }
    }
}
