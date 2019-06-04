package dvachmovie.usecase.settingsstorage

import dvachmovie.TestException
import dvachmovie.storage.SettingsStorage
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
    lateinit var useCase: GetValueCookieUseCase

    @Mock
    lateinit var settingsStorage: SettingsStorage

    @Test
    fun `Happy pass`() {
        given(settingsStorage.getCookie()).willReturn("test")
        Assert.assertEquals("test", useCase.execute(Unit))
        runBlocking {
            given(settingsStorage.getCookieAsync()).willReturn(CompletableDeferred("test"))
            Assert.assertEquals("test", useCase.executeAsync(Unit))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(settingsStorage.getCookieAsync()).willThrow(TestException())
            useCase.executeAsync(Unit)
        }
    }
}