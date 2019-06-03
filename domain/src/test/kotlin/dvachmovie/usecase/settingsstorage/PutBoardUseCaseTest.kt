package dvachmovie.usecase.settingsstorage

import dvachmovie.TestException
import dvachmovie.storage.SettingsStorage
import dvachmovie.usecase.settingsStorage.PutBoardUseCase
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
class PutBoardUseCaseTest {

    @InjectMocks
    lateinit var useCase: PutBoardUseCase

    @Mock
    lateinit var settingsStorage: SettingsStorage

    @Test
    fun `Happy pass`() {
        runBlocking {
            given(settingsStorage.putBoard("test")).willReturn(CompletableDeferred(Unit))
            Assert.assertEquals(Unit, useCase.executeAsync("test"))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(settingsStorage.putBoard("test")).willThrow(TestException())
            useCase.executeAsync("test")
        }
    }
}