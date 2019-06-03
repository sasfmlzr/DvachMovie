package dvachmovie.usecase.settingsstorage

import dvachmovie.TestException
import dvachmovie.storage.SettingsStorage
import dvachmovie.usecase.settingsStorage.PutIsLoadingEveryTimeUseCase
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
class PutIsLoadingEveryTimeUseCaseTest {

    @InjectMocks
    lateinit var useCase: PutIsLoadingEveryTimeUseCase

    @Mock
    lateinit var settingsStorage: SettingsStorage

    @Test
    fun `Happy pass`() {
        runBlocking {
            given(settingsStorage.putLoadingEveryTime(false)).willReturn(CompletableDeferred(Unit))
            Assert.assertEquals(Unit, useCase.executeAsync(false))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(settingsStorage.putLoadingEveryTime(false)).willThrow(TestException())
            useCase.executeAsync(false)
        }
    }
}