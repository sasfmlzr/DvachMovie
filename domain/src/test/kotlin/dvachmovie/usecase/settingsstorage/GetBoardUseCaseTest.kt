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
class GetBoardUseCaseTest {

    @InjectMocks
    lateinit var useCase: GetBoardUseCase

    @Mock
    lateinit var settingsStorage: SettingsStorage

    @Test
    fun `Happy pass`() {
        given(settingsStorage.getBoard()).willReturn("testBoard")
        Assert.assertEquals("testBoard", useCase.execute(Unit))
        runBlocking {
            given(settingsStorage.getBoardAsync()).willReturn(CompletableDeferred("testBoard"))

            Assert.assertEquals("testBoard", useCase.executeAsync(Unit))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(settingsStorage.getBoardAsync()).willThrow(TestException())
            useCase.executeAsync(Unit)
        }
    }
}
