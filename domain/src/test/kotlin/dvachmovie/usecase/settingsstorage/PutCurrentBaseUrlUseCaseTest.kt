package dvachmovie.usecase.settingsstorage

import dvachmovie.TestException
import dvachmovie.storage.SettingsStorage
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PutCurrentBaseUrlUseCaseTest {

    @InjectMocks
    lateinit var useCase: PutCurrentBaseUrlUseCase

    @Mock
    lateinit var settingsStorage: SettingsStorage

    @Test
    fun `Happy pass`() {
        runBlocking {
            BDDMockito.given(settingsStorage.putCurrentBaseUrl("test")).willReturn(CompletableDeferred(Unit))
            Assert.assertEquals(Unit, useCase.executeAsync("test"))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            BDDMockito.given(settingsStorage.putCurrentBaseUrl("test")).willThrow(TestException())
            useCase.executeAsync("test")
        }
    }
}
